package ru.otus.danik_ik.homework09etc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultHandler {
    void handle(ResultSet result) throws SQLException;
}