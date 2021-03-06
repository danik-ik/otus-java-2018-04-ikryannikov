package ru.otus.danik_ik.homework09.database;

import ru.otus.danik_ik.homework09.storage.DataSet;
import ru.otus.danik_ik.homework09.storage.Executor;
import ru.otus.danik_ik.homework09.storage.StorageException;
import ru.otus.danik_ik.homework09.storm.DataSetLoader;
import ru.otus.danik_ik.homework09.storm.DataSetSaver;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.function.Consumer;

public class SqlExecutor implements Executor {
    private final Connection connection;

    public SqlExecutor() throws StorageException {
        this( ConnectionHelper.getConnection() );
    }

    public SqlExecutor(Connection connection) {
        this.connection = connection;
    }

    public void execQuery(String query, ResultHandler handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            try( ResultSet result = stmt.getResultSet() ) {
                handler.handle(result);
            }
        }
    }

    public int execUpdate(String update) throws SQLException {
        return execUpdate(update, null);
    }

    public int execUpdate(String update, Consumer<PreparedStatement> paramsSetter) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(update)) {
            if (paramsSetter != null)
                paramsSetter.accept(stmt);
            stmt.execute();
            return stmt.getUpdateCount();
        }
    }
    Connection getConnection() {
        return connection;
    }

    @Override
    public <T extends DataSet> void save(T entry) throws StorageException {
        try {
            new DataSetSaver<T>(connection, entry)
                    .save();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws StorageException {
        return new DataSetLoader<T>(connection, clazz, id).load();
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    @FunctionalInterface
    public interface PreparedStatementObjSetter {
        void set(PreparedStatement stmt, Object source, int index) throws SQLException, StorageException;
    }

}
