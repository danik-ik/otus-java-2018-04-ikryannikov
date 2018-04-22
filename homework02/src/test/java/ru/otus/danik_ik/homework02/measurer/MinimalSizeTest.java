package ru.otus.danik_ik.homework02.measurer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.function.Supplier;

import static org.junit.Assert.assertTrue;

//@Ignore
@RunWith(Parameterized.class)
public class MinimalSizeTest {
    /**
     * Data for test.
     */
    @Parameterized.Parameters(name = "{index}: {2}")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
            {new String(), 40, "Empty string"},
            {randomString100(), 40, "100-char string"},
            {new long[0], 16 + 8 * 0, "array of 0 long"},
            {new long[100], 16 + 8 * 100, "array of 100 long"},
            {new String[]{"","",""}, 16 + 3 * 16, "array of 3 empty string"},
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
        System.out.println(comment + ": " + size);
        assertTrue(size >= mustBeMoreOrEqual);
    }

    private static String randomString100() {
        final char[] allowedChars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" +
                "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя").toCharArray();
        char[] sequence = new char[100];
        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = allowedChars[(int)(Math.random()*allowedChars.length)];
        }
        String result = new String(sequence);
        return result;
    }
}
