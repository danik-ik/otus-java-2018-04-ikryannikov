package ru.otus.danik_ik.homework05.testo;

import java.lang.reflect.Method;

public interface TestHandler {
    void beforeClass (Object instance, Method method);
    void runningBefore (Object instance, Method method);
    void runningTest (Object instance, Method method);
    void runningAfter (Object instance, Method method);
    void testIsOk (Object instance, Method method);
    void testIsFail (Object instance, Method method, String message);
    void testThrewException (Object instance, Method method, Throwable e);
}
