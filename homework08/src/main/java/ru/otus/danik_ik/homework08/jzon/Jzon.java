package ru.otus.danik_ik.homework08.jzon;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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

    public String serialize(Object src) {
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
        if (src instanceof Object) {
            for (Object o: (Object[])src)
                ja.add(explore(o));
        }
        return ja;

    }

    private JSONAware exploreObject(Object src) {
        JSONObject jo = new JSONObject();
        // TODO: 13.06.2018 Протись по полям 
        return jo;
    }

    public JzonType getJzonType(Object src) {
        for (Map.Entry<Class, JzonType> e: types.entrySet()) {
            if (e.getKey().isAssignableFrom(src.getClass()) ) return e.getValue();
        }
        return JzonType.OBJECT;
    }

}
