package ru.otus.danik_ik.homework09.storm;

import ru.otus.danik_ik.homework09.database.SqlExecutor;
import ru.otus.danik_ik.homework09.storm.annotations.DbField;
import ru.otus.danik_ik.homework09.storm.annotations.DbTable;
import ru.otus.danik_ik.homework09.storage.DataSet;
import ru.otus.danik_ik.homework09.storage.StorageException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

public class DataSetLoader<T extends DataSet> {
    private final Connection connection;
    private final Class<T> clazz;
    private final long id;

    public DataSetLoader(Connection connection, Class<T> clazz, long id) {
        this.connection = connection;
        this.clazz = clazz;
        this.id = id;
    }

    private Collection<Method> Setters = new LinkedList<>();

    private Map<String, SqlExecutor.ResultSetValueToObjCopier> mappers = new HashMap<>();

    private Consumer<PreparedStatement> setParamsFor;

    public T load() throws StorageException {
        collectMethods();
        buildMappers();

        T result = createTargetClassInstance();

        try {
            try (ResultSet resultSet = getData()) {
                copyToTarget(resultSet, result);
            }
        } catch (SQLException e) {
            throw new StorageException(String.format("Ошибка при загрузке данных для id = %s", id), e);
        }

        return result;
    }

    private ResultSet getData() throws SQLException, StorageException {
        PreparedStatement statement = connection.prepareStatement(prepareLoadQuery());
        statement.setLong(1, id);
        ResultSet result = statement.executeQuery();
        
        if (!result.first()) 
            throw new StorageException(String.format("Не найден объект с ID = %s", id));
        if (!result.isLast())
            throw new StorageException(String.format("Найдено более одного объекта с ID = %s", id));
        
        return result;
    }

    private String prepareLoadQuery() throws StorageException {
        final String template = "SELECT %s FROM %S WHERE ID=?";
        final String fieldsList = getFieldsList();
        return String.format(template, fieldsList, getTableName(clazz));
    }

    private String getFieldsList() {
        return String.join(",", mappers.keySet());
    }

    private String getTableName(Class<? extends DataSet> aClass) throws StorageException {
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


    private void copyToTarget(ResultSet resultSet, T target) {
        for (SqlExecutor.ResultSetValueToObjCopier copier: mappers.values()) {
            copier.execute(resultSet, target);
        }
    }

    private T createTargetClassInstance() throws StorageException {
        T result;
        try {
            result = clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException 
                | InvocationTargetException | InstantiationException e) {
            throw new StorageException(
                    String.format("Ошибка при создании экземпляра целевого класса %s", clazz.getCanonicalName()),
                    e);
        }
        return result;
    }

    private void buildMappers() {
        for (Method m : Setters) {
            DbField anno = m.getAnnotationsByType(DbField.class)[0];

            String name = anno.name();
            if (name == null) name = m.getName().substring(3);

            SqlExecutor.ResultSetValueToObjCopier action = (resultSet, target) -> {
                try {
                    Object value = anno.type().getFieldGetter().get(resultSet, anno.name());
                    m.invoke(target, value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            };
            mappers.put(name, action);
        }
    }

    private void collectMethods() {
        for (Method m : clazz.getMethods()) {
            if (isApplicableSetter(m)) {
                rememberSetter(m);
            }
        }
    }

    private void rememberSetter(Method m) {
        Setters.add(m);
    }

    private boolean isApplicableSetter(Method m) {
        if (m.getParameterCount() != 1) return false;
        if (!m.getName().startsWith("set")) return false;
        if (m.getDeclaringClass().equals(Object.class)) return false;
        if (m.getAnnotationsByType(DbField.class).length == 0) return false;
        DbField anno = m.getAnnotationsByType(DbField.class)[0];
        if (!anno.type().isCompatibleFor(m.getParameters()[0].getType())) return false;
        return true;
    }

}
