package ru.otus.danik_ik.homework05.testo;

public class Assertions {
    public static void Assert(boolean condition) {
        if (!condition) throw new AssertionError("Condition must be true");
    };

    public static void Assert(Object expected, Object actual) {
        if (!expected.equals(actual))
            throw new AssertionError(
                    String.format("Values are not equal. Expected: <%s>, but actual is <%s>", expected, actual));
    };

}
