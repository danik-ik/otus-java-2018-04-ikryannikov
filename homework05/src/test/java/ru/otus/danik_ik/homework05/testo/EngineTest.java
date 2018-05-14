package ru.otus.danik_ik.homework05.testo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EngineTest {

    @Before
    public void setUp() throws Exception {
        Counter.reset();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void pakage1() {
        Engine.execute("ru.otus.danik_ik.homework05.testo.testpackage1");
        assertEquals(1, Counter.getTestCount());
    }

    @Test
    public void pakage1Class1() {
        Engine.execute("ru.otus.danik_ik.homework05.testo.testpackage1.Class1");
        assertEquals(1, Counter.getTestCount());
    }
}