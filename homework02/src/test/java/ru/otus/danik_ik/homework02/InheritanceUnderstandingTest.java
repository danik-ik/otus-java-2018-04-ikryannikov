package ru.otus.danik_ik.homework02;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Simple parametrized test.
 */
@RunWith(Parameterized.class)
public class InheritanceUnderstandingTest
{
    /**
     * Data for test.
     */
    @Parameterized.Parameters(name = "{index}: («{0}» is super for «{1}»: {2})")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
            /*  uncomment the lines below to create test cases
                each row used as set of parameters for constructor */
            {Object.class, String.class, true},
            {String.class, Object.class, false},
            {Object.class, int.class, false},
            {Object.class, boolean.class, false},
            {Object.class, byte.class, false},
            {Object.class, short.class, false},
            {Object.class, long.class, false},
            {Object.class, float.class, false},
            {Object.class, double.class, false},
            {Object.class, Integer.class, true},
            {Object[].class, String[].class, true},
            {Object[].class, int[].class, false},
        });
    }

    /**
     * Data for current instance (one row from result of dataForTest() )
     */
    private Class superclass;
    private Class inheritedClass;
    private boolean result;

    public InheritanceUnderstandingTest(Class superclass, Class inheritedClass, boolean result) {
        this.superclass = superclass;
        this.inheritedClass = inheritedClass;
        this.result = result;
    }

    @Test
    public void testIsAssignableFrom() throws Exception {
        assertEquals(result, superclass.isAssignableFrom(inheritedClass));
    }
}
