package ru.otus.danik_ik.homework09etc.hibernateStorage;

import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.danik_ik.homework09etc.storage.dataSets.UserDataSet;
import ru.otus.danik_ik.homework09etc.hibernateStorage.dao.UserDAO;
import ru.otus.danik_ik.homework09etc.storage.DBService;
import ru.otus.danik_ik.homework09etc.storage.dataSets.AddressDataSet;
import ru.otus.danik_ik.homework09etc.storage.dataSets.PhoneDataSet;

import java.util.List;
import java.util.function.Function;

public class DbServiceImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DbServiceImpl() {
        
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
//        configuration.setProperty("hibernate.connection.username", "tully");
//        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
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
        try (Session session = sessionFactory.openSession()) {
            UserDAO dao = new UserDAO(session);
            dao.save(dataSet);
        }
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
}
