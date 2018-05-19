package ru.otus.danik_ik.homework05.testo;

import java.lang.reflect.Method;
import java.util.Collection;

class TestingEnvironment implements TestEnvironment {
    private final ClassesSupplier classesSupplier;

    private int testCount = 0;
    private int beforeCount = 0;
    private int afterCount = 0;

    private int okTestsCount = 0;
    private int failedTestsCount = 0;
    private int exceptionsInTestsCount = 0;

    public TestingEnvironment(ClassesSupplier classesSupplier) {
        this.classesSupplier = classesSupplier;
    }

    @Override
    public Collection<Class<?>> getClasses(String nameOrPrefix) {
        return classesSupplier.getClasses(nameOrPrefix);
    }

    @Override
    public void beforeClass(Object instance, Method method) {
        System.out.println("==============================================");
        System.out.println("Testing: " + instance.getClass().getSimpleName() + "." + method.getName());
        System.out.println("==============================================");
    }

    @Override
    public void runningBefore(Object instance, Method method) {
        beforeCount++;
    }

    @Override
    public void runningTest(Object instance, Method method) {
        testCount++;
    }

    @Override
    public void runningAfter(Object instance, Method method) {
        afterCount++;
    }

    @Override
    public void testIsOk(Object instance, Method method) {
        okTestsCount++;
    }

    @Override
    public void testIsFail(Object instance, Method method) {
        failedTestsCount++;
    }

    @Override
    public void testThrewException(Object instance, Method method, Exception e) {
        exceptionsInTestsCount++;
    }

    public int getTestCount() {
        return testCount;
    }

    public int getBeforeCount() {
        return beforeCount;
    }

    public int getAfterCount() {
        return afterCount;
    }

    public int getOkTestsCount() {
        return okTestsCount;
    }

    public int getFailedTestsCount() {
        return failedTestsCount;
    }

    public int getExceptionsInTestsCount() {
        return exceptionsInTestsCount;
    }
}

