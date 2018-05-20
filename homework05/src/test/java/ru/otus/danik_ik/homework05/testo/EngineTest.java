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
    private TestingEnvironment environment;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPackageBefore() throws Exception {
        environment = new TestingEnvironment(new testingtestPackageSupplier());
        Engine.execute(environment, "ru.otus.danik_ik.homework05.testo.testingtest");
        assertEquals(2, environment.getBeforeCount());
    }

    @Test
    public void testPackageAfter() throws Exception {
        environment = new TestingEnvironment(new testingtestPackageSupplier());
        Engine.execute(environment, "ru.otus.danik_ik.homework05.testo.testingtest");
        assertEquals(5, environment.getAfterCount());
    }

    @Test
    public void testPackageTests() throws Exception {
        environment = new TestingEnvironment(new testingtestPackageSupplier());
        Engine.execute(environment, "ru.otus.danik_ik.homework05.testo.testingtest");
        assertEquals(5, environment.getTestCount());
    }

    @Test
    public void testPackageTestsOk() throws Exception {
        environment = new TestingEnvironment(new testingtestPackageSupplier());
        Engine.execute(environment, "ru.otus.danik_ik.homework05.testo.testingtest");
        assertEquals(3, environment.getOkTestsCount());
    }

    @Test
    public void testPackageTestsFailed() throws Exception {
        environment = new TestingEnvironment(new testingtestPackageSupplier());
        Engine.execute(environment, "ru.otus.danik_ik.homework05.testo.testingtest");
        assertEquals(1, environment.getFailedTestsCount());
    }

    @Test
    public void testPackageTestsExceptions() throws Exception {
        environment = new TestingEnvironment(new testingtestPackageSupplier());
        Engine.execute(environment, "ru.otus.danik_ik.homework05.testo.testingtest");
        assertEquals(1, environment.getExceptionsInTestsCount());
    }

    @Test
    public void testClass1Before() throws Exception {
        environment = new TestingEnvironment(new Class1Supplier());
        Engine.execute(environment, "ru.otus.danik_ik.homework05.testo.testingtest.Class1");
        assertEquals(2, environment.getBeforeCount());
    }

    @Test
    public void testClass1After() throws Exception {
        environment = new TestingEnvironment(new Class1Supplier());
        Engine.execute(environment, "ru.otus.danik_ik.homework05.testo.testingtest.Class1");
        assertEquals(2, environment.getAfterCount());
    }

    @Test
    public void testClass1Tests() throws Exception {
        environment = new TestingEnvironment(new Class1Supplier());
        Engine.execute(environment, "ru.otus.danik_ik.homework05.testo.testingtest.Class1");
        assertEquals(2, environment.getTestCount());
    }


    private class testingtestPackageSupplier implements ClassesSupplier {
        @Override
        public Collection<Class<?>> getClasses(String nameOrPrefix) {
            return Arrays.asList(new Class<?>[]{Class1.class, Class2.class});
        }
    }

    private class Class1Supplier implements ClassesSupplier {
        @Override
        public Collection<Class<?>> getClasses(String nameOrPrefix) {
            return Collections.singletonList(Class1.class);
        }
    }

}