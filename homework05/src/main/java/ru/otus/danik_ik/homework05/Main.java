package ru.otus.danik_ik.homework05;

import ru.otus.danik_ik.homework05.environment.KnownClassesSupplier;
import ru.otus.danik_ik.homework05.environment.SimpleTestEnvironment;
import ru.otus.danik_ik.homework05.testo.Engine;
import ru.otus.danik_ik.homework05.testo.TestEnvironment;
import ru.otus.danik_ik.homework05.testo.TestException;

public class Main
{
    private static TestEnvironment environment = new SimpleTestEnvironment(new KnownClassesSupplier());
    private static final String TARGETS[][] = {
            {"ru.otus.danik_ik.homework05.testo.test1","Пакет"},
            {"ru.otus.danik_ik.homework05.testo.test2.Class1","Класс"},
            {"ru.otus.danik_ik.homework05.testo.test1.Class2","Класс"},
    };

    public static void main( String[] args ) throws ClassNotFoundException {
        for (String[] target: TARGETS) {
            System.out.println();
            System.out.println("*****************************************************************************");
            System.out.println("запуск с параметром " + target[0] + " (" + target[1] + ")");
            System.out.println("*****************************************************************************");
            test(target[0]);
        }
    }

    private static void test(String target) {
        try {
            Engine.execute(environment, target);
        } catch (TestException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
