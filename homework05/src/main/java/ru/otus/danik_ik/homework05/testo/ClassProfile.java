package ru.otus.danik_ik.homework05.testo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassProfile {
    private Method beforeMethod;
    private Method afterMethod;
    private List<Method> testMethods = new ArrayList<>();
    private Object instance;

    public ClassProfile(Class target) {
        // TODO: 16.05.2018 разобрать методы по аннотациям
    }

    public boolean isTest() { return !testMethods.isEmpty(); }

    public void createInstance() {}
    public void executeBefore() {}
    public void executeTests() {}
    public void executeAfter() {}
}
