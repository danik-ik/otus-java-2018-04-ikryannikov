package ru.otus.danik_ik.homework09.database;

import ru.otus.danik_ik.homework09.storage.DataSet;
import ru.otus.danik_ik.homework09.storage.Executor;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

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
        new DataSetSaver<T>(connection, entry)
                .save();
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return null;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    private class DataSetSaver<T extends DataSet> {
        private final Connection connection;
        private final T source;

        public DataSetSaver(Connection connection, T source) {
            this.connection = connection;
            this.source = source;
        }

        public void save() {
            buildQuery();
            applyParameters();
            executePrepared();
        }

        private Collection<Method> rowGetters = new LinkedList<>();
        private Collection<Method> keyGetters = new LinkedList<>();

        private void buildQuery() {
            // найти геттеры с аннотацией DbField
            // Построить запрос, набрать коллекцию лямбд для установки значений
        }

        private void applyParameters() {
        }

        private void executePrepared() {
        }

        ;
    }
}
