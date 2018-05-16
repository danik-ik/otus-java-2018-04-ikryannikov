package ru.otus.danik_ik.homework05.testo.test2;

import ru.otus.danik_ik.homework05.testo.*;

public class Class1 {
    @Before
    public void before() {
        System.out.println("before method in ru.otus.danik_ik.homework05.testo.test2.Class1");
    }

    @After
    public void after() {
        System.out.println("after method in ru.otus.danik_ik.homework05.testo.test2.Class1");
    }

    @Test
    public void test1() {
        System.out.println("test1 method in ru.otus.danik_ik.homework05.testo.test2.Class1");
    }

    @Test
    public void test2() {
        System.out.println("test2 method in ru.otus.danik_ik.homework05.testo.test2.Class1");
    }
}
