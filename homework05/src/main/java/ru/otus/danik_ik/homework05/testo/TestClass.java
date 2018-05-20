package ru.otus.danik_ik.homework05.testo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestClass {
    private final TestEnvironment environment;
    private final Class target;
    private final Constructor defaultConstructor;
    private Method beforeMethod;
    private Method afterMethod;
    private List<Method> testMethods = new ArrayList<>();

    public TestClass(Class target, TestEnvironment environment) {
        this.environment = environment;
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
            return target + "\n>>> Не тестовый класс: отсутствует конструктор по умолчанию";
        if (testMethods.isEmpty())
            return target + "\n>>> Не тестовый класс: отсутствуют публичные методы без параметров с аннотацией @Test";
        return target + "\n>>> Это класс с тестами";
    }


    public void executeTests() throws IllegalAccessException, InstantiationException, InvocationTargetException, TestExecutionException {
        environment.beforeClass(isTest(), getClassDescription());
        for (Method test: testMethods){
            Object instance = createInstance();
            environment.createdInstance(instance, test);
            executeBefore(instance);
            executeTest(instance, test);
            executeAfter(instance);
        }
    }

    private void executeTest(Object instance, Method test) throws InvocationTargetException, IllegalAccessException {
        environment.runningTest(instance, test);
        try {
            test.invoke(instance);
        } catch (IllegalAccessException e) {
            throw e;
        } catch (InvocationTargetException wrapper) {
            Throwable e = wrapper.getTargetException();
            if (e instanceof AssertionError){
                environment.testIsFail(instance, test, e.getMessage());
                return;
            }
            environment.testThrewException(instance, test, e);
            return;
        }
        environment.testIsOk(instance, test);
    }

    private Object createInstance() throws IllegalAccessException, InvocationTargetException, InstantiationException, TestExecutionException {
        try {
            Constructor constructor = target.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new TestExecutionException(e);
        }
        return defaultConstructor.newInstance();

    }

    private void executeBefore(Object instance) throws InvocationTargetException, IllegalAccessException {
        if (beforeMethod == null) return;
        environment.runningBefore(instance, beforeMethod);
        beforeMethod.invoke(instance);
    }

    private void executeAfter(Object instance) throws InvocationTargetException, IllegalAccessException {
        if (afterMethod == null) return;
        environment.runningAfter(instance, afterMethod);
        afterMethod.invoke(instance);
    }
}
