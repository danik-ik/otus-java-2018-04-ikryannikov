package ru.otus.danik_ik.homework05.testo.testingtest;

import ru.otus.danik_ik.homework05.testo.After;
import ru.otus.danik_ik.homework05.testo.Counter;
import ru.otus.danik_ik.homework05.testo.Test;

public class Class2 {
    @After
    public void after() {
        Counter.countAfter();
    }

    @Test
    public void test1() {
        Counter.countTest();
    }

    @Test
    public void test2() {
        Counter.countTest();
    }

    @Test
    public void test3() {
        Counter.countTest();
    }
}
