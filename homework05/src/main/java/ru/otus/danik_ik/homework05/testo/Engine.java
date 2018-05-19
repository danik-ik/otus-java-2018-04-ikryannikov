package ru.otus.danik_ik.homework05.testo;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemClassLoader;

public class Engine {
    private final String target;
    private final ClassesSupplier classesSupplier;

    private Engine(ClassesSupplier classesSupplier, String target) throws TargetNotFoundException, TestExecutionException {
        this.classesSupplier = classesSupplier;
        this.target = target;
        execute();
    };

    public static void execute(ClassesSupplier classesSupplier, String target) throws TargetNotFoundException, TestExecutionException {
        new Engine(classesSupplier, target);
    }

    private void execute() throws TargetNotFoundException, TestExecutionException {
        Collection<Class<?>> classes = classesSupplier.get(target);
        if (classes.isEmpty())
            throw new TargetNotFoundException("Параметр должен быть полным именем класса или пакета.\n" +
                    "Если это пакет, предварительно должен быть загружен хотя бы один класс из пакета.");
        for (Class<?> c: classes) runTestsInClass(c);
    };

    private void runTestsInClass(Class c) throws TestExecutionException {
        TestClass test = new TestClass(c);
        if (!test.isTest()) {
            System.out.println(test.getClassDescription());
            return;
        }
        try {
            test.executeTests();
        } catch (Exception e) {
            throw new TestExecutionException(e);
        }
    }
}
