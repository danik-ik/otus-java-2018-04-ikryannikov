package ru.otus.danik_ik.homework09.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
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
            }
    ),
    FLOAT( classArray(Float.class, float.class),
            (stmt, value, index) -> {
            try {
                stmt.setFloat(index, (Float) value);
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
        }
    );

    private final PreparedStatementValSetter preparedStatementValSetter;
    private final Set<Class<?>> compatibleJavaClasses;

    DbFieldType(Set<Class<?>> compatibleJavaClasses, PreparedStatementValSetter preparedStatementValSetter) {
        this.compatibleJavaClasses = compatibleJavaClasses;
        this.preparedStatementValSetter = preparedStatementValSetter;
    }

    public PreparedStatementValSetter getPreparedStatementValSetter() {
        return preparedStatementValSetter;
    }

    public boolean isCompatibleFor(Class<?> clazz) {
        return compatibleJavaClasses.contains(clazz);
    }

    @FunctionalInterface
    public interface PreparedStatementValSetter {
        void set(PreparedStatement stmt, Object value, int index);
    }

    private static Set<Class<?>> classArray(Class<?>... classes) {
        Set<Class<?>> result = new HashSet<>();
        result.addAll(Arrays.asList(classes));
        return result;
    }
}
