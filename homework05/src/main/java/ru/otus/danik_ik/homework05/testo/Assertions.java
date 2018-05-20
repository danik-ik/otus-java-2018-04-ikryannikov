package ru.otus.danik_ik.homework05.testo;

public class Assertions {
    public static void assertTrue(boolean condition) {
        if (!condition) throw new AssertionError("Condition must be true");
    };

    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual))
            throw new AssertionError(
                    String.format("Values are not equal. Expected: <%s>, but actual is <%s>", expected, actual));
    };

}
