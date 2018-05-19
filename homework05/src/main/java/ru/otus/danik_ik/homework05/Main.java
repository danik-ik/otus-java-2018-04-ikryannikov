package ru.otus.danik_ik.homework05;

import ru.otus.danik_ik.homework05.environment.SimpleTestEnvironment;
import ru.otus.danik_ik.homework05.testo.Engine;
import ru.otus.danik_ik.homework05.testo.TestEnvironment;
import ru.otus.danik_ik.homework05.testo.TestException;

public class Main
{
    private static TestEnvironment environment = new SimpleTestEnvironment();
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
            Engine.execute(environment, target);
        } catch (TestException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
