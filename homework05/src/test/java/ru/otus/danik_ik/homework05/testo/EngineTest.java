package ru.otus.danik_ik.homework05.testo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.danik_ik.homework05.testo.testingtest.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
    public void testPackageBefore() throws Exception {
        Engine.execute(new testingtestPackageSupplier(), "ru.otus.danik_ik.homework05.testo.testingtest");
        assertEquals(2, Counter.getBeforeCount());
    }

    @Test
    public void testPackageAfter() throws Exception {
        Engine.execute(new testingtestPackageSupplier(), "ru.otus.danik_ik.homework05.testo.testingtest");
        assertEquals(5, Counter.getAfterCount());
    }

    @Test
    public void testPackageTests() throws Exception {
        Engine.execute(new testingtestPackageSupplier(), "ru.otus.danik_ik.homework05.testo.testingtest");
        assertEquals(5, Counter.getTestCount());
    }

    @Test
    public void testClass1Before() throws Exception {
        Engine.execute(new Class1Supplier(), "ru.otus.danik_ik.homework05.testo.testingtest.Class1");
        assertEquals(2, Counter.getBeforeCount());
    }

    @Test
    public void testClass1After() throws Exception {
        Engine.execute(new Class1Supplier(), "ru.otus.danik_ik.homework05.testo.testingtest.Class1");
        assertEquals(2, Counter.getAfterCount());
    }

    @Test
    public void testClass1Tests() throws Exception {
        Engine.execute(new Class1Supplier(), "ru.otus.danik_ik.homework05.testo.testingtest.Class1");
        assertEquals(2, Counter.getTestCount());
    }


    private class testingtestPackageSupplier implements ClassesSupplier {
        @Override
        public Collection<Class<?>> get(String NameORPrefix) {
            return Arrays.asList(new Class<?>[]{Class1.class, Class2.class});
        }
    }

    private class Class1Supplier implements ClassesSupplier {
        @Override
        public Collection<Class<?>> get(String NameORPrefix) {
            return Collections.singletonList(Class1.class);
        }
    }

}