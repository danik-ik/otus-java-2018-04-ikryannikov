package ru.otus.danik_ik.homework05.testo.test1;

import ru.otus.danik_ik.homework05.testo.*;
import ru.otus.danik_ik.homework05.testo.annotations.After;
import ru.otus.danik_ik.homework05.testo.annotations.Before;
import ru.otus.danik_ik.homework05.testo.annotations.Test;

public class Class2 {
    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void testOk() {
        Assertions.assertEquals("QWERTY", "QWE" + "RTY");
    }

    @Test
    public void testFail() {
        Assertions.assertEquals("ЙЦУКЕН", "QWE" + "RTY");
    }

    @Test
    public void test3() {
    }
}
