package ru.otus.danik_ik.homework05.testo;

import java.lang.reflect.Method;
import java.util.Collection;

class TestingEnvironment implements TestEnvironment {
    private final ClassesSupplier classesSupplier;

    private static int testCount = 0;
    private static int beforeCount = 0;
    private static int afterCount = 0;

    private static int okTestsCount = 0;
    private static int failedTestsCount = 0;
    private static int exceptionsInTestsCount = 0;

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

    public static int getTestCount() {
        return testCount;
    }

    public static int getBeforeCount() {
        return beforeCount;
    }

    public static int getAfterCount() {
        return afterCount;
    }

    public static int getOkTestsCount() {
        return okTestsCount;
    }

    public static int getFailedTestsCount() {
        return failedTestsCount;
    }

    public static int getExceptionsInTestsCount() {
        return exceptionsInTestsCount;
    }
}

