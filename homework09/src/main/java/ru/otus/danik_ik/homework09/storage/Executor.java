package ru.otus.danik_ik.homework09.storage;

import ru.otus.danik_ik.homework09.DataSet;

public interface Executor {
    <T extends DataSet> void save(T entry);
    <T extends DataSet> T load(long id, Class<T> clazz);
}
