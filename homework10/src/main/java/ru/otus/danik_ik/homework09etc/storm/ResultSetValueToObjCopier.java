package ru.otus.danik_ik.homework09etc.storm;

import ru.otus.danik_ik.homework09etc.storage.StorageException;

import java.sql.ResultSet;

@FunctionalInterface
public interface ResultSetValueToObjCopier {
    void execute(ResultSet resultSet, Object target) throws StorageException;
}

