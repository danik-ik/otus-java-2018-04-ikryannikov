package ru.otus.danik_ik.homework08.jzon;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

public class Jzon {
    private Map<Class, JzonType> types = new LinkedHashMap<>();
    {
        types.put(String.class, JzonType.VALUE);
        types.put(Number.class, JzonType.VALUE);
        types.put(Object[].class, JzonType.ARRAY);
        types.put(short[].class, JzonType.ARRAY);
        types.put(int[].class, JzonType.ARRAY);
        types.put(long[].class, JzonType.ARRAY);
        types.put(float[].class, JzonType.ARRAY);
        types.put(double[].class, JzonType.ARRAY);
        types.put(char[].class, JzonType.ARRAY);
        types.put(byte[].class, JzonType.ARRAY);
        types.put(Collection.class, JzonType.ARRAY);
        types.put(Map.class, JzonType.MAP);
    }

    public String toJson(Object src) {
        return explore(src).toJSONString();
    }

    public JSONAware explore(Object src) {
        switch (getJzonType(src)) {
            case VALUE: return new JSONAware() {
                @Override
                public String toJSONString() {
                    return JSONValue.toJSONString(src);
                }
            };
            case ARRAY: return exploreArray(src);
            case MAP: return exploreMap(src);
            default: return exploreObject(src);
        }
    }

    private JSONAware exploreMap(Object src) {
        JSONObject jo = new JSONObject();
        jo.putAll((Map)src);
        return jo;
    }

    private JSONAware exploreArray(Object src) {
        JSONArray ja = new JSONArray();
        if (src instanceof Object[]) {
            for (Object o: (Object[])src)
                ja.add(explore(o));
        } else if (src instanceof int[]) {
            for (int it: (int[])src)
                ja.add(it);
        } else if (src instanceof short[]) {
            for (short it: (short[])src)
                ja.add(it);
        } else if (src instanceof long[]) {
            for (long it: (long[])src)
                ja.add(it);
        } else if (src instanceof float[]) {
            for (float it: (float[])src)
                ja.add(it);
        } else if (src instanceof double[]) {
            for (double it: (double[])src)
                ja.add(it);
        } else if (src instanceof char[]) {
            for (char it: (char[])src)
                ja.add(it);
        } else if (src instanceof byte[]) {
            for (byte it: (byte[])src)
                ja.add(it);
        }
        return ja;

    }

    private JSONAware exploreObject(Object src) {
        JSONObject jo = new JSONObject();

        Class<?> it = src.getClass();
        
        Map<String, Object> fieldValues = new LinkedHashMap<>();

        for (Field f: it.getFields()) {
            // TODO: 14.06.2018 transient 
            try {
                fieldValues.put(f.getName(), f.get(src));
            } catch (IllegalAccessException e) {
                throw new JzonException("Как я сюда попал?!", e);
            }
        }

//        Method[] methods = it.getMethods();
//        for (Method m: methods) {
//            // только для геттеров без параметров
//            if ( m.getParameterCount() == 0
//            && m.getName().startsWith("get") ) {
//                Object returned = null;
//                System.out.println(m.getName());
//                try {
//                    returned = m.invoke(src);
//                } catch (InvocationTargetException | IllegalAccessException e) {
//                    //throw new JzonException("Как я сюда попал?!", e);
//                }
//                fieldValues.put(m.getName().substring(3), returned);
//            }    
//        }
        // TODO: 14.06.2018 Зацикливается, собака. Разобраться и устранить. Не вызывать геттеры из Object? 

        for (Map.Entry<String, Object> e: fieldValues.entrySet()) {
            if (e.getValue() == null) continue;
            
            if (getJzonType(e.getValue()) == JzonType.VALUE) {
                jo.put(e.getKey(), e.getValue());
            } else {
                jo.put(e.getKey(), explore(e.getValue()));
            };
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
