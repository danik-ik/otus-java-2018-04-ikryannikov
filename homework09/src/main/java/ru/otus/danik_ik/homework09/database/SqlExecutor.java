package ru.otus.danik_ik.homework09.database;

import ru.otus.danik_ik.homework09.storage.DataSet;
import ru.otus.danik_ik.homework09.storage.Executor;
import ru.otus.danik_ik.homework09.storage.StorageException;

import java.lang.reflect.Method;
import java.sql.*;

public class SqlExecutor implements Executor {
    private final Connection connection;

    public SqlExecutor() {
        this( ConnectionHelper.getConnection() );
    }

    public SqlExecutor(Connection connection) {
        this.connection = connection;
    }

    public void execQuery(String query, ResultHandler handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            handler.handle(result);
        }
    }

    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
    }

    Connection getConnection() {
        return connection;
    }

    @Override
    public <T extends DataSet> void save(T entry) {
        try {
            new DataSetSaver<T>(connection, entry)
                    .save();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return new DataSetLoader<T>(connection, clazz, id).load();
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    @FunctionalInterface
    public interface PreparedStatementObjSetter {
        void set(PreparedStatement stmt, Object source, int index);
    }

    @FunctionalInterface
    public interface TargetObjSetter {
        void set(Method m, Object tsrget, int index);
    }

    @FunctionalInterface
    public interface ResultSetValueToObjCopier {
        void execute(ResultSet resultSet, Object target);
    }
}
