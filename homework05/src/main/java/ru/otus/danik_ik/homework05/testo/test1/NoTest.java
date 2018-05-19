package ru.otus.danik_ik.homework05.testo.test1;

import ru.otus.danik_ik.homework05.testo.After;
import ru.otus.danik_ik.homework05.testo.Before;

public class NoTest {
    @Before
    public void before() {
        System.out.println("before method in ru.otus.danik_ik.homework05.testo.test1.Class1");
    }

    @After
    public void after() {
        System.out.println("after method in ru.otus.danik_ik.homework05.testo.test1.Class1");
    }

}
