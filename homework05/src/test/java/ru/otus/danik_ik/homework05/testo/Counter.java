package ru.otus.danik_ik.homework05.testo;

public class Counter {
    private static int testCount = 0;
    private static int beforeCount = 0;
    private static int afterCount = 0;

    public static void  countBefore() { beforeCount++; }
    public static void  countTest() { testCount++; }
    public static void  countAfter() { afterCount++; }

    public static void reset() {
        testCount = 0;
        beforeCount = 0;
        afterCount = 0;
    }

    public static int getTestCount() {
        return testCount;
    }

    public static int getBeforeCount() {
        return beforeCount;
    }

    public static int getAfterCount() {
        return afterCount;
    }
}
