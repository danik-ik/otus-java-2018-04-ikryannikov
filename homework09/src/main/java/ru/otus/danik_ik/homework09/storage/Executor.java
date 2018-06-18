package ru.otus.danik_ik.homework09.storage;

public interface Executor extends AutoCloseable {
    <T extends DataSet> void save(T entry);
    <T extends DataSet> T load(long id, Class<T> clazz);
}
