package ru.otus.danik_ik.homework08.jzon;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.*;
import java.util.*;

public class Jzon {
    private final static Map<Class, JzonType> types = new HashMap<>();
    static {
        types.put(String.class, JzonType.VALUE);
        types.put(Number.class, JzonType.VALUE);
        types.put(Boolean.class, JzonType.VALUE);
        types.put(Character.class, JzonType.VALUE);
        types.put(Byte.class, JzonType.VALUE);
        types.put(Object[].class, JzonType.ARRAY);
        types.put(short[].class, JzonType.ARRAY);
        types.put(int[].class, JzonType.ARRAY);
        types.put(long[].class, JzonType.ARRAY);
        types.put(float[].class, JzonType.ARRAY);
        types.put(double[].class, JzonType.ARRAY);
        types.put(char[].class, JzonType.ARRAY);
        types.put(byte[].class, JzonType.ARRAY);
        types.put(boolean[].class, JzonType.ARRAY);
        types.put(Collection.class, JzonType.ARRAY);
        types.put(Map.class, JzonType.MAP);
    }

    // для исключения зацикливания
    private Set branch = new HashSet();

    public String toJson(Object src) {
        return explore(src).toJSONString();
    }

    public JSONAware explore(Object src) {
        if (branch.contains(src))
            throw new JzonException( String.format("Зацикливание: %s", src));
        branch.add(src);

        try {
            switch (getJzonType(src)) {
                case VALUE: return () -> valueAsJsonString(src);
                case ARRAY: return exploreArray(src);
                case MAP: return exploreMap(src);
                default: return exploreObject(src);
            }
        } finally {
            branch.remove(src);
        }
    }

    public static String valueAsJsonString(Object src) {
        if (src instanceof Character)
            return JSONValue.toJSONString(src.toString());
        return JSONValue.toJSONString(src);
    }

    private JSONAware exploreMap(Object src) {
        JSONObject jo = new JSONObject();
        jo.putAll((Map)src);
        return jo;
    }

    private JSONAware exploreArray(Object src) {
        JSONArray ja = new JSONArray();
        for(int i = 0, length = Array.getLength(src); i < length; ++i) 
            ja.add(explore(Array.get(src, i)));
        return ja;
    }

    private JSONAware exploreObject(Object src) {
        JSONObject jo = new JSONObject();

        Class<?> it = src.getClass();
        Map<String, Object> fieldValues = new LinkedHashMap<>();

        collectFieldValues(src, it, fieldValues);
        collectValuesOfGetters(src, it, fieldValues);

        for (Map.Entry<String, Object> e: fieldValues.entrySet()) {
            if (e.getValue() == null) continue;
            jo.put(e.getKey(), explore(e.getValue()));
        }

        return jo;
    }

    private void collectValuesOfGetters(Object src, Class<?> aClass, Map<String, Object> fieldValues) {
        Method[] methods = aClass.getMethods();
        for (Method m: methods) {
            // только для геттеров без параметров, не входящих в Object
            if ( !isApplicableGetter(m) ) continue;
            fieldValues.put( getFieldNameByGetter(m), getValueByGetter(src, m) );
        }
    }

    private boolean isApplicableGetter(Method m) {
        if (m.getParameterCount() > 0) return false;
        if ( !m.getName().startsWith("get") ) return false;
        if (m.getDeclaringClass().equals(Object.class)) return false;
        return true;
    }

    private Object getValueByGetter(Object src, Method m) {
        try {
            return m.invoke(src);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new JzonException("Как я сюда попал?!", e);
        }
    }

    private String getFieldNameByGetter(Method m) {
        return m.getName().substring(3,4).toLowerCase()
                + m.getName().substring(4);
    }

    private void collectFieldValues(Object src, Class<?> aClass, Map<String, Object> fieldValues) {
        for (Field f: aClass.getFields()) {
            if (( f.getModifiers() & Modifier.TRANSIENT) > 0) continue;

            try {
                fieldValues.put(f.getName(), f.get(src));
            } catch (IllegalAccessException e) {
                throw new JzonException("Как я сюда попал?!", e);
            }
        }
    }

    public JzonType getJzonType(Object src) {
        for (Map.Entry<Class, JzonType> e: types.entrySet()) {
            if (e.getKey().isAssignableFrom(src.getClass()) ) return e.getValue();
        }
        return JzonType.OBJECT;
    }

}
