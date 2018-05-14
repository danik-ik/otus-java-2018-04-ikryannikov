package ru.otus.danik_ik.homework05;

import java.lang.String;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.*;

/** Simple parametrized test. */
@RunWith(Parameterized.class)
public class MainTest
{
    /** Data for test. */
    @Parameterized.Parameters(name = "{index}: («{1}»)")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
            /*  uncomment the lines below to create test cases
                each row used as set of parameters for constructor */
            // {"AAA", "AAA"},
            // {"BBB", "bbb"},
            // {"CCC", "Ccc"},
        });
    }

    /**
     * Data for current instance (one row from result of dataForTest() )
     */
    private String expected;
    private String input;

    public MainTest(String expected, String input) {
        this.expected = expected;
        this.input = input;
    }

    @Test
    public void testUppercase() throws Exception {
        assertEquals(expected, input.toUpperCase());
    }
}
