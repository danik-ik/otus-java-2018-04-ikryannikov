package ru.otus.danik_ik.homework05.testo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassProfile {
    private final Class target;
    private final Constructor defaultConstructor;
    private Method beforeMethod;
    private Method afterMethod;
    private List<Method> testMethods = new ArrayList<>();
    private Object instance;

    public ClassProfile(Class target) {
        Constructor defaultConstructor;
        this.target = target;
        try {
            defaultConstructor = target.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            defaultConstructor = null;
        }
        this.defaultConstructor = defaultConstructor;
        if (this.defaultConstructor == null) return;

        analizeAnnotations();
    }

    private void analizeAnnotations() {

    }

    public boolean isTest() {
        return defaultConstructor != null && !testMethods.isEmpty();
    }

    public void executeTests() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        createInstance();
        executeBefore();
        executeEachTest();
        executeAfter();
    }

    private void createInstance() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            Constructor constructor = target.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        instance = defaultConstructor.newInstance();
    }
    private void executeBefore() {}
    private void executeEachTest() {}
    private void executeAfter() {}
}
