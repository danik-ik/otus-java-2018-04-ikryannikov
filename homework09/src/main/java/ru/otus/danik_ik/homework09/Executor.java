package ru.otus.danik_ik.homework09;

import ru.otus.danik_ik.homework09.DataSet;

import java.sql.SQLException;

public class Executor {
    <T extends DataSet> void save(T entry) throws SQLException {
    }
    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        return null;
    }
}
