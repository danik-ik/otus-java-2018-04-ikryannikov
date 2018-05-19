package ru.otus.danik_ik.homework05.testo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.danik_ik.homework05.testo.testpackage1.Class1;

import static org.junit.Assert.*;

public class EngineTest {
    static { new Class1(); }

    @Before
    public void setUp() throws Exception {
        Counter.reset();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void pakage1() throws Exception {
        Engine.execute(new KnownClassesSupplier(), "ru.otus.danik_ik.homework05.testo.testpackage1");
        assertEquals(1, Counter.getTestCount());
    }

    @Test
    public void pakage1Class1() throws Exception {
        Engine.execute(new KnownClassesSupplier(), "ru.otus.danik_ik.homework05.testo.testpackage1.Class1");
        assertEquals(1, Counter.getTestCount());
    }
    
    @Test(expected = TargetNotFoundException.class)
    public void testTargetNotFound() throws Exception {
        Engine.execute(new KnownClassesSupplier(), "neverexisting");
    }
}