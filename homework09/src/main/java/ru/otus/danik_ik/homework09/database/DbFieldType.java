package ru.otus.danik_ik.homework09.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum DbFieldType {
    LONG( (stmt, value, index) -> {
                try {
                    stmt.setLong(index, (Long)value);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
    ),
    FLOAT( (stmt, value, index) -> {
            try {
                stmt.setFloat(index, (Float) value);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    ),
    STRING( (stmt, value, index) -> {
            try {
                stmt.setString(index, (String)value);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    ),
    DATE( (stmt, value, index) -> {
            try { // TODO: 19.06.2018  преобразовать к java.sql.Date корректно 
                stmt.setDate(index, (Date) value);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    );

    private final PreparedStatementValSetter preparedStatementValSetter;

    DbFieldType(PreparedStatementValSetter preparedStatementValSetter) {
        this.preparedStatementValSetter = preparedStatementValSetter;
    }

    public PreparedStatementValSetter getPreparedStatementValSetter() {
        return preparedStatementValSetter;
    }

    @FunctionalInterface
    public interface PreparedStatementValSetter {
        void set(PreparedStatement stmt, Object value, int index);
    }

}
