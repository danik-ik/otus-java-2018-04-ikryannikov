package ru.otus.danik_ik.homework05.testo;

import static java.lang.ClassLoader.getSystemClassLoader;

public class Engine {
    private final String target;

    public static void execute(String target) throws TargetNotFoundException {
        new Engine(target);
    }

    private Engine(String target) throws TargetNotFoundException {
        this.target = target;
        if ( runAsPackage() ) return;
        if ( runAsClass() ) return;
        throw new TargetNotFoundException("Параметр должен быть полным именем класса или пакета.\n" +
                "Если это пакет, предварительно должен быть загружен хотя бы один класс из пакета.");
    };

    boolean runAsPackage() {
        Package it = getSystemClassLoader().getDefinedPackage(target);
        if (it == null) return false;

        // TODO: 15.05.2018 <<<< 
        return true;
    }

    boolean runAsClass() {
        try {
            Class<?> it = Class.forName(target);
        } catch (ClassNotFoundException e) {
            return false;
        }

        // TODO: 15.05.2018 <<<<  
        return true;
    }
}
