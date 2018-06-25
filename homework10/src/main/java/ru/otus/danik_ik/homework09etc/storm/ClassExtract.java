package ru.otus.danik_ik.homework09etc.storm;

import ru.otus.danik_ik.homework09etc.database.SqlExecutor;
import ru.otus.danik_ik.homework09etc.storage.StorageException;
import ru.otus.danik_ik.homework09etc.storm.annotations.DbField;
import ru.otus.danik_ik.homework09etc.storm.annotations.DbTable;
import ru.otus.danik_ik.homework09etc.storm.annotations.ID;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

class ClassExtract {
    private static Map<Class<?>, ClassExtract> registry = new HashMap<>();

    public static ClassExtract get(Class<?> clazz) throws StorageException {
        ClassExtract extract = registry.get(clazz);
        if (extract != null)
            return extract;
        extract = new ClassExtract(clazz);
        registry.put(clazz, extract);
        return extract;
    };

    private final Class<?> clazz;
    private final String tableName;

    private final Collection<Method> nonKeyGetters = new LinkedList<>();
    private final Collection<Method> keyGetters = new LinkedList<>();
    private final Collection<Method> Setters = new LinkedList<>();

    private final Map<String, SqlExecutor.PreparedStatementObjSetter> nonKeyMappers = new HashMap<>();
    private final Map<String, SqlExecutor.PreparedStatementObjSetter> keyMappers = new HashMap<>();
    private final Map<String, ResultSetValueToObjCopier> dbToObjMappers = new HashMap<>();

    private ClassExtract(Class<?> clazz) throws StorageException {
        this.clazz = clazz;
        tableName = getTableName(clazz);

        collectMethods();
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

    public Collection<Method> getSetters() {
        return Collections.unmodifiableCollection(Setters);
    }

    public Map<String, ResultSetValueToObjCopier> getDbToObjMappers() {
        return Collections.unmodifiableMap(dbToObjMappers);
    }

    private void collectMethods() {
        for (Method m : clazz.getMethods()) {
            if (isApplicableGetter(m)) {

                if (isIdMethod(m))
                    keyGetters.add(m);
                else
                    nonKeyGetters.add(m);
            } else if (isApplicableSetter(m)) {
                rememberSetter(m);
            }
        }
    }

    private boolean isIdMethod(Method m) {
        return m.getAnnotationsByType(ID.class).length > 0;
    }

    private void buildMappers() {
        buildMapper(nonKeyGetters, nonKeyMappers);
        buildMapper(keyGetters, keyMappers);
        buildLoaderMappers();
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
                } catch (IllegalAccessException | InvocationTargetException
                        | SQLException e) {
                    throw new StorageException(e);
                }
            };
            target.put(name, action);
        }
    }

    private void buildLoaderMappers() {
        for (Method m : Setters) {
            DbField anno = m.getAnnotationsByType(DbField.class)[0];

            String name = anno.name();
            if (name == null) name = m.getName().substring(3);

            ResultSetValueToObjCopier action = (resultSet, target) -> {
                try {
                    Object value = anno.type().getFieldGetter().get(resultSet, anno.name());
                    m.invoke(target, value);
                } catch (IllegalAccessException | InvocationTargetException
                        | SQLException e) {
                    throw new StorageException(e);
                }
            };
            dbToObjMappers.put(name, action);
        }
    }

    private boolean isApplicableGetter(Method m) {
        if (m.getParameterCount() > 0) return false;
        if (!m.getName().startsWith("get")) return false;
        if (m.getDeclaringClass().equals(Object.class)) return false;
        if (m.getAnnotationsByType(DbField.class).length == 0) return false;
        return true;
    }

    public String getTableName(Class<?> aClass) throws StorageException {
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
