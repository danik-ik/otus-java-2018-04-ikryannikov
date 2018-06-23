package ru.otus.danik_ik.homework09.database;

import ru.otus.danik_ik.homework09.storage.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    static Connection getConnection() throws StorageException {
        try {
            DriverManager.registerDriver(new org.h2.Driver());

            String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
