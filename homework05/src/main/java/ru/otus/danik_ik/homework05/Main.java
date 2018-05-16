package ru.otus.danik_ik.homework05;

import ru.otus.danik_ik.homework05.testo.Engine;
import ru.otus.danik_ik.homework05.testo.TargetNotFoundException;
import ru.otus.danik_ik.homework05.testo.TestException;

public class Main
{
    private static final String TARGET1 = "ru.otus.danik_ik.homework05.testo.test1";
    private static final String TARGET1_CLASS = "ru.otus.danik_ik.homework05.testo.test1.Class1";
    private static final String TARGET2 = "ru.otus.danik_ik.homework05.testo.test2.Class1";
    private static final String TARGET3 = "ru.otus.danik_ik.homework05.testo.test1.Class2";

    public static void main( String[] args ) throws ClassNotFoundException {
        // Предварительно загружается класс, иначе не будет доступен пакет
        Class.forName(TARGET1_CLASS);
        test(TARGET1, "package");
        test(TARGET2, "class");
        test(TARGET3, "class");
    }

    private static void test(String target, String comment) {
        System.out.println("=================================================");
        System.out.printf("%s: %s\n", comment, target);
        System.out.println("=================================================");

        try {
            Engine.execute(target);
        } catch (TestException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
