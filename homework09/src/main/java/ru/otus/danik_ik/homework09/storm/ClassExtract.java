package ru.otus.danik_ik.homework09.storm;

import ru.otus.danik_ik.homework09.database.SqlExecutor;
import ru.otus.danik_ik.homework09.storage.DataSet;
import ru.otus.danik_ik.homework09.storage.StorageException;
import ru.otus.danik_ik.homework09.storm.annotations.DbField;
import ru.otus.danik_ik.homework09.storm.annotations.DbTable;
import ru.otus.danik_ik.homework09.storm.annotations.ID;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

class ClassExtract {
    private static Map<Class<?>, ClassExtract> registry = new HashMap<>();

    public static ClassExtract get(Class<?> clazz) {
        return registry.computeIfAbsent(clazz, ClassExtract::new);
    };

    private final Class<?> clazz;
    private final String tableName;

    private final Collection<Method> nonKeyGetters = new LinkedList<>();
    private final  Collection<Method> keyGetters = new LinkedList<>();

    private final  Map<String, SqlExecutor.PreparedStatementObjSetter> nonKeyMappers = new HashMap<>();
    private final  Map<String, SqlExecutor.PreparedStatementObjSetter> keyMappers = new HashMap<>();

    private ClassExtract(Class<?> clazz) {
        this.clazz = clazz;
        try {
            tableName = getTableName(clazz);
        } catch (StorageException e) {
            throw new RuntimeException(e);
        }

        collectGetters();
        buildMappers();
    }

    public Collection<Method> getNonKeyGetters() {
        return Collections.unmodifiableCollection(nonKeyGetters);
    }

    public Collection<Method> getKeyGetters() {
        return Collections.unmodifiableCollection(keyGetters);
    }

    public Map<String, SqlExecutor.PreparedStatementObjSetter> getNonKeyMappers() {
        return Collections.unmodifiableMap(nonKeyMappers);
    }

    public Map<String, SqlExecutor.PreparedStatementObjSetter> getKeyMappers() {
        return Collections.unmodifiableMap(keyMappers);
    }

    public String getTableName() {
        return tableName;
    }

    private void collectGetters() {
        for (Method m : clazz.getMethods()) {
            if (!isApplicableGetter(m)) continue;

            if (isIdMethod(m))
                keyGetters.add(m);
            else
                nonKeyGetters.add(m);
        }
    }

    private boolean isIdMethod(Method m) {
        return m.getAnnotationsByType(ID.class).length > 0;
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

    private String getTableName(Class<?> aClass) throws StorageException {
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

}
