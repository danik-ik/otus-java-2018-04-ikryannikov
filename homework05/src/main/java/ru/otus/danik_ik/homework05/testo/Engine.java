package ru.otus.danik_ik.homework05.testo;

import java.util.Collection;

public class Engine {
    private final String target;
    private final TestEnvironment testEnvironment;

    private Engine(TestEnvironment testEnvironment, String target) throws TargetNotFoundException, TestExecutionException {
        this.testEnvironment = testEnvironment;
        this.target = target;
        execute();
    };

    public static void execute(TestEnvironment testEnvironment, String target) throws TargetNotFoundException, TestExecutionException {
        new Engine(testEnvironment, target);
    }

    private void execute() throws TargetNotFoundException, TestExecutionException {
        Collection<Class<?>> classes = testEnvironment.getClasses(target);
        if (classes.isEmpty())
            throw new TargetNotFoundException("Параметр должен быть полным именем класса или пакета.\n" +
                    "Если это пакет, предварительно должен быть загружен хотя бы один класс из пакета.");
        for (Class<?> c: classes) runTestsInClass(c);
    };

    private void runTestsInClass(Class c) throws TestExecutionException {
        TestClass test = new TestClass(c);
        System.out.println("---------------------------------");
        System.out.println(test.getClassDescription());
        if (test.isTest())
            try {
                test.executeTests();
            } catch (Exception e) {
                throw new TestExecutionException(e);
            }
    }
}
