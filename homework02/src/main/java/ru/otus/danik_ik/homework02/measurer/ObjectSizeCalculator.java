package ru.otus.danik_ik.homework02.measurer;

import ru.otus.danik_ik.homework02.agent.ObjectSizeFetcher;

import java.lang.reflect.Field;
import java.util.*;

public class ObjectSizeCalculator {
    private Set<Object> visited = new HashSet<>();
    private long accumalator = 0;

    private ObjectSizeCalculator() {}

    public static long calcSize(Object object) {
        return new ObjectSizeCalculator().calcSizeImpl(object);
    }

    public long calcSizeImpl(Object object) {
        addObjectSize(object);
        return accumalator;
    }

    private void addObjectSize(Object object) {
        if (isVisited(object)) return;;
        visited.add(object);

        accumalator += ObjectSizeFetcher.getObjectSize(object);
        addMembersIfObjectArray(object);
        addRelatedObjects(object);
    }

    private void addMembersIfObjectArray(Object object) {
        if (isObjectArray(object))
            addArrayMembers(object);
    }

    private boolean isObjectArray(Object object) {
        return object instanceof Object[];
    }

    private void addArrayMembers(Object object) {
        for (Object member: (Object[])object) {
            addObjectSize(member);
        }
    }

    private void addRelatedObjects(Object parent) {
        for (Object child: getReferences(parent)) {
            addObjectSize(child);
        }
    }

    private boolean isVisited(Object o) {
        return visited.contains(o);
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
