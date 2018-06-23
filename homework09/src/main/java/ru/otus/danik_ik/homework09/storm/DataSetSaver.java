package ru.otus.danik_ik.homework09.storm;

import ru.otus.danik_ik.homework09.database.SqlExecutor;
import ru.otus.danik_ik.homework09.storage.DataSet;
import ru.otus.danik_ik.homework09.storage.StorageException;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.otus.danik_ik.homework09.storage.DataSet.UNDEFINED_ID;

public class DataSetSaver<T extends DataSet> {
    private final Connection connection;
    private final T source;
    private final ClassExtract extract;

    public DataSetSaver(Connection connection, T source) throws StorageException {
        this.connection = connection;
        this.source = source;
        extract = ClassExtract.get(source.getClass());
    }

    public void save() throws SQLException, StorageException {
        if (isInsertion())
            doInsert();
        else
            doUpdate();
    }

    private PreparedStatementConsumer setParamsFor;

    private void doInsert() throws SQLException, StorageException {
        String query = prepareInsertQuery();

        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        setParamsFor.accept(statement);
        statement.execute();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                source.setID(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating DataSet failed, no ID obtained.");
            }
        }
    }

    private void doUpdate() throws SQLException, StorageException {
        String query = prepareUpdateQuery();

        PreparedStatement statement = connection.prepareStatement(query);
        setParamsFor.accept(statement);
        statement.execute();
    }

    private boolean isInsertion() {
        return source.getID() == UNDEFINED_ID;
    }

    private String prepareInsertQuery() {
        final String template = "INSERT INTO %s (%s) VALUES(%s)";

        Map<String, SqlExecutor.PreparedStatementObjSetter> nonKeyMappers = extract.getNonKeyMappers();

        String fieldNames = String.join(",", nonKeyMappers.keySet());
        String placeholders = String.join(",", getNPlaceholders(nonKeyMappers.size()));

        setParamsFor = statement -> {
            int i = 1;
            for (SqlExecutor.PreparedStatementObjSetter setter : nonKeyMappers.values()) {
                setter.set(statement, source, i);
                i++;
            }
        };

        return String.format(template, extract.getTableName(), fieldNames, placeholders);
    }

    private List<String> getNPlaceholders(int size) {
        return Stream.generate(() -> "?").limit(size).collect(Collectors.toList());
    }

    private String prepareUpdateQuery() {
        final String template = "UPDATE %s SET %S WHERE %s";

        Map<String, SqlExecutor.PreparedStatementObjSetter> nonKeyMappers = extract.getNonKeyMappers();
        Map<String, SqlExecutor.PreparedStatementObjSetter> keyMappers = extract.getKeyMappers();

        String fieldsAssignments = String.join(",",
                nonKeyMappers.keySet().stream()
                        .map(name -> name + "=?")
                        .collect(Collectors.toList()));
        String keyCondition = String.join(" AND ",
                keyMappers.keySet().stream()
                        .map(name -> name + "=?")
                        .collect(Collectors.toList()));
        setParamsFor = statement -> {
            int i = 1;
            for (SqlExecutor.PreparedStatementObjSetter setter : nonKeyMappers.values()) {
                setter.set(statement, source, i);
                i++;
            }
            for (SqlExecutor.PreparedStatementObjSetter setter : keyMappers.values()) {
                setter.set(statement, source, i);
                i++;
            }
        };

        return String.format(template, extract.getTableName(), fieldsAssignments, keyCondition);
    }

    @FunctionalInterface
    public interface PreparedStatementConsumer {
        public void accept(PreparedStatement stmt) throws SQLException, StorageException;
    }

}
