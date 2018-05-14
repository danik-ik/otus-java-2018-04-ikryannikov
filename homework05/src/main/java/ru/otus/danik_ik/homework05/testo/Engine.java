package ru.otus.danik_ik.homework05.testo;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Engine {
    private final String target;

    public static void execute(String target) {
        new Engine(target);
    }

    private Engine(String target) {
        this.target = target;
        if ( runAsPackage() ) return;
        if ( runAsClass() ) return;
        System.out.println("Параметр должен быть полным именем класса или пакета");
    };

    boolean runAsPackage() {
        return false;
    }

    boolean runAsClass() {
        return false;
    }
}
