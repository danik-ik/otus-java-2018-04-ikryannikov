package ru.otus.danik_ik.homework05.testo.test1;

import ru.otus.danik_ik.homework05.testo.*;


public class NoBefore {
    @After
    public void after() {
        System.out.println("after method in ru.otus.danik_ik.homework05.testo.test1.NoBefore");
    }

    @Test
    public void test1() {
        System.out.println("test1 method in ru.otus.danik_ik.homework05.testo.test1.NoBefore");
    }

    @Test
    public void test2() {
        System.out.println("test2 method in ru.otus.danik_ik.homework05.testo.test1.NoBefore");
    }
    @Test
    public void test3() {
        System.out.println("test3 method in ru.otus.danik_ik.homework05.testo.test1.NoBefore");
    }

}
