package ru.otus.danik_ik.homework05.testo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestClass {
    private final Class target;
    private final Constructor defaultConstructor;
    private Method beforeMethod;
    private Method afterMethod;
    private List<Method> testMethods = new ArrayList<>();
    private Object instance;

    public TestClass(Class target) {
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
        Method[] methods = target.getMethods();
        for (Method m: methods) {
            if (m.getGenericParameterTypes().length != 0) continue;
            if ( m.getAnnotation(Before.class) != null ) beforeMethod = m;
            if ( m.getAnnotation(After.class) != null ) afterMethod = m;
            if ( m.getAnnotation(Test.class) != null ) testMethods.add(m);
        }

    }

    public boolean isTest() {
        return defaultConstructor != null && !testMethods.isEmpty();
    }

    public String getClassDescription() {
        if (defaultConstructor == null)
            return "Не тестовый класс: отсутствует конструктор по умолчанию";
        if (testMethods.isEmpty())
            return "Не тестовый класс: отсутствуют публичные методы без параметров с аннотацией @Test";
        return "Это класс с тестами";
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

    private void executeBefore() throws InvocationTargetException, IllegalAccessException {
        if (beforeMethod == null) return;
        beforeMethod.invoke(instance);
    }

    private void executeEachTest() throws InvocationTargetException, IllegalAccessException {
        for (Method m : testMethods)
            m.invoke(instance);
    }

    private void executeAfter() throws InvocationTargetException, IllegalAccessException {
        if (afterMethod == null) return;
        afterMethod.invoke(instance);
    }
}
