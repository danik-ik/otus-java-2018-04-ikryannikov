package ru.otus.danik_ik.homework05.testo;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        return false;
    }

    boolean runAsClass() {
        return false;
    }
}
