package ru.otus.danik_ik.homework05.testo.testingtest;

import ru.otus.danik_ik.homework05.testo.After;
import ru.otus.danik_ik.homework05.testo.Assertions;
import ru.otus.danik_ik.homework05.testo.Test;

public class Class2 {
    @After
    public void after() {
    }

    @Test
    public void test1() {
        Assertions.assertTrue(true);
    }

    @Test
    public void test2() {
        Assertions.assertTrue(false);
    }

    @Test
    public void test3() throws Exception {
        throw new Exception("Поймай меня");
    }
}
