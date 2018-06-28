package ru.otus.danik_ik.homework09etc.storage;

import ru.otus.danik_ik.homework09etc.storage.dataSets.UserDataSet;

import java.util.List;

public interface DBService extends AutoCloseable {
    void save(UserDataSet dataSet);
    UserDataSet read(long id);
    UserDataSet readByName(String name);
    List<UserDataSet> readAll();
}
