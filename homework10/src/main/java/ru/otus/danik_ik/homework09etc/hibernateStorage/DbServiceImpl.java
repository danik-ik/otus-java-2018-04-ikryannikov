package ru.otus.danik_ik.homework09etc.hibernateStorage;

import org.hibernate.SessionFactory;
import ru.otus.danik_ik.homework09etc.UserDataSet;
import ru.otus.danik_ik.homework09etc.storage.DBService;

import java.util.List;

public class DbServiceImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DbServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(UserDataSet dataSet) {
        
    }

    @Override
    public UserDataSet read(long id) {
        return null;
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
}
