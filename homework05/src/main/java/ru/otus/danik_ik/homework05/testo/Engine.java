package ru.otus.danik_ik.homework05.testo;

import java.lang.reflect.InvocationTargetException;

import static java.lang.ClassLoader.getSystemClassLoader;

public class Engine {
    private final String target;

    public static void execute(String target) throws TargetNotFoundException, TestExecutionException {
        new Engine(target);
    }

    private Engine(String target) throws TargetNotFoundException, TestExecutionException {
        this.target = target;
        if ( runAsPackage() ) return;
        if ( runAsClass() ) return;
        throw new TargetNotFoundException("Параметр должен быть полным именем класса или пакета.\n" +
                "Если это пакет, предварительно должен быть загружен хотя бы один класс из пакета.");
    };

    boolean runAsPackage() {
        Package it = getSystemClassLoader().getDefinedPackage(target);
        if (it == null) return false;

        System.out.println("This is a package!");
        // TODO: 15.05.2018 <<<< 
        return true;
    }

    boolean runAsClass() throws TestExecutionException {
        Class<?> it;
        try {
            it = Class.forName(target);
        } catch (ClassNotFoundException e) {
            return false;
        }

        System.out.println("This is a class!");
        runTestsInClass(it);
        return true;
    }

    private void runTestsInClass(Class c) throws TestExecutionException {
        ClassProfile profile = new ClassProfile(c);
        if (!profile.isTest()) {
            System.out.println(profile.getClassDescription());
            return;
        }
        try {
            profile.executeTests();
        } catch (Exception e) {
            throw new TestExecutionException(e);
        }
    }
}
