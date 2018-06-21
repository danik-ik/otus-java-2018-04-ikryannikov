package ru.otus.danik_ik.homework09.database;

import ru.otus.danik_ik.homework09.database.annotations.DbField;
import ru.otus.danik_ik.homework09.database.annotations.DbTable;
import ru.otus.danik_ik.homework09.storage.DataSet;
import ru.otus.danik_ik.homework09.storage.StorageException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.otus.danik_ik.homework09.storage.DataSet.UNDEFINED_ID;

class DataSetSaver<T extends DataSet> {
    private final Connection connection;
    private final T source;
    private final String tableName;

    public DataSetSaver(Connection connection, T source) {
        this.connection = connection;
        this.source = source;
        tableName = getTableName(source.getClass());
    }

    private String getTableName(Class<? extends DataSet> aClass) {
        DbTable[] annotations = aClass.getAnnotationsByType(DbTable.class);
        String tableName;
        if (annotations.length == 0)
            throw new StorageException(String.format("Класс %s не имеет аннотации DbTable", aClass.getName()));
        tableName = annotations[0].name();
        if (tableName == null)
            throw new StorageException(String.format("в аннотации DbTable к классу %s не указано имя таблицы",
                    aClass.getName()));
        return tableName;
    }

    public void save() throws SQLException {
        collectGetters();
        buildMappers();

        if (isInsertion())
            doInsert();
        else
            doUpdate();
    }

    private Collection<Method> nonKeyGetters = new LinkedList<>();
    private Collection<Method> keyGetters = new LinkedList<>();

    private Map<String, SqlExecutor.PreparedStatementObjSetter> nonKeyMappers = new HashMap<>();
    private Map<String, SqlExecutor.PreparedStatementObjSetter> keyMappers = new HashMap<>();

    private Consumer<PreparedStatement> setParamsFor;

    private void doInsert() throws SQLException {
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

    private void doUpdate() throws SQLException {
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

        String fieldNames = String.join(",", nonKeyMappers.keySet());
        String placeholders = String.join(",", getNPlaceholders(nonKeyMappers.size()));

        setParamsFor = statement -> {
            int i = 1;
            for (SqlExecutor.PreparedStatementObjSetter setter : nonKeyMappers.values()) {
                setter.set(statement, source, i);
                i++;
            }
        };

        return String.format(template, tableName, fieldNames, placeholders);
    }

    private List<String> getNPlaceholders(int size) {
        return Stream.generate(() -> "?").limit(size).collect(Collectors.toList());
    }

    private String prepareUpdateQuery() {
        final String template = "UPDATE %s SET %S WHERE %s";
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

        return String.format(template, tableName, fieldsAssignments, keyCondition);
    }

    private void collectGetters() {
        for (Method m : source.getClass().getMethods()) {
            if (!isApplicableGetter(m)) continue;
            DbField anno = m.getAnnotationsByType(DbField.class)[0];
            if (anno.isKey())
                keyGetters.add(m);
            else
                nonKeyGetters.add(m);
        }
    }

    private void buildMappers() {
        buildMapper(nonKeyGetters, nonKeyMappers);
        buildMapper(keyGetters, keyMappers);
    }

    private void buildMapper(Collection<Method> source, Map<String, SqlExecutor.PreparedStatementObjSetter> target) {
        for (Method m : source) {
            DbField anno = m.getAnnotationsByType(DbField.class)[0];

            String name = anno.name();
            if (name == null) name = m.getName().substring(3);

            SqlExecutor.PreparedStatementObjSetter action = (stmt, obj, index) -> {
                try {
                    Object value = m.invoke(obj);
                    anno.type().getPreparedStatementValSetter().set(stmt, value, index);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            };
            target.put(name, action);
        }
    }

    private boolean isApplicableGetter(Method m) {
        if (m.getParameterCount() > 0) return false;
        if (!m.getName().startsWith("get")) return false;
        if (m.getDeclaringClass().equals(Object.class)) return false;
        if (m.getAnnotationsByType(DbField.class).length == 0) return false;
        return true;
    }
}
