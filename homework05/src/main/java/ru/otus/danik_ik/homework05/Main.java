package ru.otus.danik_ik.homework05;

import ru.otus.danik_ik.homework05.testo.ClassesSupplier;
import ru.otus.danik_ik.homework05.testo.Engine;
import ru.otus.danik_ik.homework05.testo.KnownClassesSupplier;
import ru.otus.danik_ik.homework05.testo.TestException;

public class Main
{
    private static ClassesSupplier classesSupplier = new KnownClassesSupplier();
    private static final String TARGETS[] = {
            "ru.otus.danik_ik.homework05.testo.test1",
            "ru.otus.danik_ik.homework05.testo.test2.Class1",
            "ru.otus.danik_ik.homework05.testo.test1.Class2",
    };

    public static void main( String[] args ) throws ClassNotFoundException {
        for (String target: TARGETS) test(target);
    }

    private static void test(String target) {
        System.out.println("=================================================");
        System.out.println(target);
        System.out.println("=================================================");

        try {
            Engine.execute(classesSupplier, target);
        } catch (TestException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
