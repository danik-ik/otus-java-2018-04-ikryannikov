package ru.otus.danik_ik.homework02.measurer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.function.Supplier;

import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class MinimalSizeTest {
    /**
     * Data for test.
     */
    @Parameterized.Parameters(name = "{index}: {2}")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
            {new String(), 40, "Empty string"},
            {new long[0], 16 + 8 * 0, "array of 0 long"},
            {new long[100], 16 + 8 * 100, "array of 100 long"},
        });
    }

    private Object object;
    private int mustBeMoreOrEqual;
    private String comment;

    public MinimalSizeTest(Object object, int mustBeMoreOrEqual, String comment) {
        this.object = object;
        this.mustBeMoreOrEqual = mustBeMoreOrEqual;
        this.comment = comment;
    }

    private static Object get(Supplier<Object> lambda) {
        return lambda.get();
    }

    @Test
    public void minSizeTest() throws Exception {
        long size = ObjectSizeCalculator.calcSize(object);
        assertTrue(size >= mustBeMoreOrEqual);
        System.out.println(comment + ": " + size);
    }

}
