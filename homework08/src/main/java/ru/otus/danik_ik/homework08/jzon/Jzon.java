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
                case VALUE: return () -> JSONValue.toJSONString(src);
                case ARRAY: return exploreArray(src);
                case MAP: return exploreMap(src);
                default: return exploreObject(src);
            }
        } finally {
            branch.remove(src);
        }
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

        for (Field f: it.getFields()) {
            if (( f.getModifiers() & Modifier.TRANSIENT) > 0) continue;

            try {
                fieldValues.put(f.getName(), f.get(src));
            } catch (IllegalAccessException e) {
                throw new JzonException("Как я сюда попал?!", e);
            }
        }

        Method[] methods = it.getMethods();
        for (Method m: methods) {
            // только для геттеров без параметров, не входящих в Object
            if (m.getParameterCount() > 0) continue;
            if ( !m.getName().startsWith("get")) continue;
            if (m.getDeclaringClass().equals(Object.class)) continue;

            Object returned = null;
            try {
                returned = m.invoke(src);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new JzonException("Как я сюда попал?!", e);
            }
            fieldValues.put(m.getName().substring(3,4).toLowerCase()
                    + m.getName().substring(4), returned);
        }

        for (Map.Entry<String, Object> e: fieldValues.entrySet()) {
            if (e.getValue() == null) continue;
            jo.put(e.getKey(), explore(e.getValue()));
        }

        return jo;
    }

    public JzonType getJzonType(Object src) {
        for (Map.Entry<Class, JzonType> e: types.entrySet()) {
            if (e.getKey().isAssignableFrom(src.getClass()) ) return e.getValue();
        }
        return JzonType.OBJECT;
    }

}
