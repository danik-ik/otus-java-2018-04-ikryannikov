package ru.otus.danik_ik.homework09.database;

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
                try {
                    stmt.setLong(index, (Long)value);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            },
            (resultSet, fieldName) -> {
                try {
                    return resultSet.getLong(fieldName);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } 
    ),
    FLOAT( classArray(Float.class, float.class),
            (stmt, value, index) -> {
                try {
                    stmt.setFloat(index, (Float) value);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            },
            (resultSet, fieldName) -> {
                try {
                    return resultSet.getFloat(fieldName);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
    ),
    STRING( classArray(String.class),
            (stmt, value, index) -> {
                try {
                    stmt.setString(index, (String)value);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            },
            (resultSet, fieldName) -> {
                try {
                    return resultSet.getString(fieldName);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
    ),
    DATE( classArray(LocalDate.class),
            (stmt, value, index) -> {
                try {
                    java.sql.Date sqlDate = java.sql.Date.valueOf((LocalDate)value);
                    stmt.setDate(index, sqlDate);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            },
            (resultSet, fieldName) -> {
                try {
                    return resultSet.getDate(fieldName).toLocalDate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
        void set(PreparedStatement stmt, Object value, int index);
    }

    @FunctionalInterface
    public interface FieldGetter {
        Object get(ResultSet resultSet, String fieldName);
    }

    private static Set<Class<?>> classArray(Class<?>... classes) {
        Set<Class<?>> result = new HashSet<>();
        result.addAll(Arrays.asList(classes));
        return result;
    }
}
