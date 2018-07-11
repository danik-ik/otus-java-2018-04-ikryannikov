package ru.otus.danik_ik.homework11.hibernateStorage;

import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.danik_ik.homework11.storage.dataSets.UserDataSet;
import ru.otus.danik_ik.homework11.hibernateStorage.dao.UserDAO;
import ru.otus.danik_ik.homework11.storage.DBService;
import ru.otus.danik_ik.homework11.storage.dataSets.AddressDataSet;
import ru.otus.danik_ik.homework11.storage.dataSets.PhoneDataSet;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DbServiceImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DbServiceImpl() {
        
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

//        setMySqlHibernateProperties(configuration);
        setH2HibernateProperties(configuration);

        sessionFactory = createSessionFactory(configuration);
    }

    private void setCommonHibernateProperties(Configuration configuration) {
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
    }

    private void setMySqlHibernateProperties(Configuration configuration) {
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/danik_ik");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        setCommonHibernateProperties(configuration);
    }

    private void setH2HibernateProperties(Configuration configuration) {
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        setCommonHibernateProperties(configuration);
    }

    public DbServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void save(UserDataSet dataSet) {
        runInSession(session -> {
            UserDAO dao = new UserDAO(session);
            dao.save(dataSet);
        });
    }

    @Override
    public UserDataSet read(long id) {
        return runInSession(session -> {
            UserDAO dao = new UserDAO(session);
            return dao.read(id);
        });
    }

    @Override
    public UserDataSet readByName(String name) {
        return null;
    }

    @Override
    public List<UserDataSet> readAll() {
        return null;
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    private void runInSession(Consumer<Session> executor) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            executor.accept(session);
            transaction.commit();
        }
    }

}
