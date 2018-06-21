package ru.otus.danik_ik.homework09.database;

import ru.otus.danik_ik.homework09.storage.DataSet;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

class DataSetLoader<T extends DataSet> {
    private final Connection connection;
    private final Class<T> clazz;
    private final long id;

    public DataSetLoader(Connection connection, Class<T> clazz, long id) {
        this.connection = connection;
        this.clazz = clazz;
        this.id = id;
    }

    private Collection<Method> nonKeySetters = new LinkedList<>();
    private Collection<Method> keySetters = new LinkedList<>();

    private Map<String, SqlExecutor.ResultSetReader> NonKeyMappers = new HashMap<>();
    private Map<String, SqlExecutor.ResultSetReader> keyMappers = new HashMap<>();

    private Consumer<PreparedStatement> setParamsFor;

    public T load() {
        return null;
    }
}
