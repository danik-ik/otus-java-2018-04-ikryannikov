package ru.otus.danik_ik.homework09.storm;

import ru.otus.danik_ik.homework09.database.SqlExecutor;
import ru.otus.danik_ik.homework09.storage.DataSet;
import ru.otus.danik_ik.homework09.storage.StorageException;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSetLoader<T extends DataSet> {
    private final Connection connection;
    private final Class<T> clazz;
    private final long id;
    private final ClassExtract extract;

    public DataSetLoader(Connection connection, Class<T> clazz, long id) throws StorageException {
        this.connection = connection;
        this.clazz = clazz;
        this.id = id;
        extract = ClassExtract.get(clazz);
    }


    public T load() throws StorageException {
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
        return String.format(template, fieldsList, extract.getTableName(clazz));
    }

    private String getFieldsList() {
        return String.join(",", extract.getDbToObjMappers().keySet());
    }

    private void copyToTarget(ResultSet resultSet, T target) throws StorageException {
        for (SqlExecutor.ResultSetValueToObjCopier copier: extract.getDbToObjMappers().values()) {
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

}
