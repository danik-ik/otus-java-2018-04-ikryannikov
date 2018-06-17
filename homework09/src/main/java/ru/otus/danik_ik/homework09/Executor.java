package ru.otus.danik_ik.homework09;

import ru.otus.danik_ik.homework09.DataSet;

import java.sql.SQLException;

public class Executor {
    <T extends DataSet> void save(T entry) throws SQLException {
        entry.save();
    }
    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        T entry = null;
        try {
            entry = clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        entry.load(id);
        return entry;
    }
}
