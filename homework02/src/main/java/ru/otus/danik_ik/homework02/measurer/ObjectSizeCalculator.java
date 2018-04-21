package ru.otus.danik_ik.homework02.measurer;

import ru.otus.danik_ik.homework02.agent.ObjectSizeFetcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
        for (Object child: getReferences()) {
            addAbsend(child);
        }
    }

    private void addAbsend(Object o) {
        if (!visited.containsKey(o))
            addObjectSize(o);
    }

    private Iterable<? extends Object> getReferences() {
        return Collections.EMPTY_LIST;
    }

    ;
}
