package ru.otus.danik_ik.homework05.testo.test1;

import ru.otus.danik_ik.homework05.testo.After;
import ru.otus.danik_ik.homework05.testo.Before;

public class NoTest {
    @Before
    public void before() throws Exception {
        throw new Exception("@Before метод в классе без тестов вызываться не должен");
    }

    @After
    public void after() throws Exception {
        throw new Exception("@After метод в классе без тестов вызываться не должен");
    }

}
