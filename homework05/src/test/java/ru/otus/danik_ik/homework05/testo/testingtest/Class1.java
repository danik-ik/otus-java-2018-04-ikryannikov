package ru.otus.danik_ik.homework05.testo.testingtest;

import ru.otus.danik_ik.homework05.testo.*;

public class Class1 {
    @Before
    public void before() {
        Counter.countBefore();
    }

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
}
