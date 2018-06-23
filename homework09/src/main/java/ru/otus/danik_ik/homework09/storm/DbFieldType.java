package ru.otus.danik_ik.homework09.storm;

import ru.otus.danik_ik.homework09.storage.StorageException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum DbFieldType {
    LONG( classArray(Long.class, long.class), 
            (stmt, value, index) -> {
                    stmt.setLong(index, (Long)value);
            },
            (resultSet, fieldName) -> {
                    return resultSet.getLong(fieldName);
            }
    ),
    FLOAT( classArray(Float.class, float.class),
            (stmt, value, index) -> {
                    stmt.setFloat(index, (Float) value);
            },
            (resultSet, fieldName) -> {
                    return resultSet.getFloat(fieldName);
            }
    ),
    STRING( classArray(String.class),
            (stmt, value, index) -> {
                    stmt.setString(index, (String)value);
            },
            (resultSet, fieldName) -> {
                    return resultSet.getString(fieldName);
            }
    ),
    DATE( classArray(LocalDate.class),
            (stmt, value, index) -> {
                    java.sql.Date sqlDate = java.sql.Date.valueOf((LocalDate)value);
                    stmt.setDate(index, sqlDate);
            },
            (resultSet, fieldName) -> {
                    return resultSet.getDate(fieldName).toLocalDate();
            }
    );

    private final PreparedStatementValSetter preparedStatementValSetter;
    private final Set<Class<?>> compatibleJavaClasses;
    private final FieldGetter fieldGetter;

    DbFieldType(Set<Class<?>> compatibleJavaClasses, 
                PreparedStatementValSetter preparedStatementValSetter, 
                FieldGetter fieldGetter) {
        this.compatibleJavaClasses = compatibleJavaClasses;
        this.preparedStatementValSetter = preparedStatementValSetter;
        this.fieldGetter = fieldGetter;
    }

    public PreparedStatementValSetter getPreparedStatementValSetter() {
        return preparedStatementValSetter;
    }

    public boolean isCompatibleFor(Class<?> clazz) {
        return compatibleJavaClasses.contains(clazz);
    }

    public FieldGetter getFieldGetter() {
        return fieldGetter;
    }

    @FunctionalInterface
    public interface PreparedStatementValSetter {
        void set(PreparedStatement stmt, Object value, int index) throws SQLException;
    }

    @FunctionalInterface
    public interface FieldGetter {
        Object get(ResultSet resultSet, String fieldName) throws SQLException;
    }

    private static Set<Class<?>> classArray(Class<?>... classes) {
        Set<Class<?>> result = new HashSet<>();
        result.addAll(Arrays.asList(classes));
        return result;
    }
}
