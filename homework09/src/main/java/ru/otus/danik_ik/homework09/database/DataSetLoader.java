package ru.otus.danik_ik.homework09.database;

import ru.otus.danik_ik.homework09.database.annotations.DbField;
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
    private Collection<Method> keyGetters = new LinkedList<>();

    private Map<String, SqlExecutor.ResultSetReader> NonKeyMappers = new HashMap<>();
    private Map<String, SqlExecutor.PreparedStatementObjSetter> keyMappers = new HashMap<>();

    private Consumer<PreparedStatement> setParamsFor;

    public T load() {
        collectMethods();
        buildMappers();

        return null;
    }

    private void collectMethods() {
        for (Method m : clazz.getMethods()) {
            if (isApplicableGetter(m)) {
                rememberGetter(m);
            }
            if (isApplicableSetter(m)) {
                rememberGetter(m);
            }
        }
    }

    private void rememberGetter(Method m) {
        keyGetters.add(m);
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

    private boolean isApplicableGetter(Method m) {
        if (m.getParameterCount() > 0) return false;
        if (!m.getName().startsWith("get")) return false;
        if (m.getDeclaringClass().equals(Object.class)) return false;
        if (m.getAnnotationsByType(DbField.class).length == 0) return false;
        DbField anno = m.getAnnotationsByType(DbField.class)[0]; 
        if (!anno.isKey()) return false;
        return true;
    }
}
