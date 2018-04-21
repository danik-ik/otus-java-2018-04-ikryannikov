package ru.otus.danik_ik.homework02.measurer;

import ru.otus.danik_ik.homework02.agent.ObjectSizeFetcher;

import java.lang.reflect.Field;
import java.util.*;

public class ObjectSizeCalculator {
    private Map<Object, Object> visited = new HashMap<>();
    private long accumalator = 0;

    private ObjectSizeCalculator() {}

    public static long calcSize(Object object) {
        return new ObjectSizeCalculator().addObjectSize(object);
    }

    private long addObjectSize(Object object) {
        accumalator += ObjectSizeFetcher.getObjectSize(object);
        addRelatedObjects(object);

        return accumalator;
    }

    private void addRelatedObjects(Object parent) {
        for (Object child: getReferences(parent)) {
            addAbsend(child);
        }
    }

    private void addAbsend(Object o) {
        if (!visited.containsKey(o))
            addObjectSize(o);
    }

    private Iterable<? extends Object> getReferences(Object object) {
        List<Object> result = new LinkedList<>();
        Class<?> it = object.getClass();

        for (Field f: it.getDeclaredFields()) {
            Object child = getObjectOrNull(object, f);
            if (child != null) result.add(child);
        }
        return result;
    }

    private Object getObjectOrNull(Object o, Field f) {
        if (Object.class.isAssignableFrom(f.getType())) {
            try {
                f.setAccessible(true);
                return f.get(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
