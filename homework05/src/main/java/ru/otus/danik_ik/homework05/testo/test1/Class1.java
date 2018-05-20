package ru.otus.danik_ik.homework05.testo.test1;

import ru.otus.danik_ik.homework05.testo.annotations.After;
import ru.otus.danik_ik.homework05.testo.annotations.Before;
import ru.otus.danik_ik.homework05.testo.annotations.Test;

import static ru.otus.danik_ik.homework05.testo.Assertions.assertTrue;

public class Class1 {
    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void testOk() {
        assertTrue(true);
    }

    @Test
    public void testFail() {
        assertTrue(false);
    }

    @Test
    public void exceptionInTest() throws Exception {
        throw new Exception("Поймай меня!");
    }
}
