package ru.otus.danik_ik.homework05.testo;

public class Counter {
    private static int testCount = 0;

    public static synchronized void  CountTest() { testCount++; }
    public static void reset() {
        testCount = 0;
    }

    public static int getTestCount() {
        return testCount;
    }
}
