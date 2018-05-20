package ru.otus.danik_ik.homework05.testo;

import java.lang.reflect.Method;

public interface TestHandler {
    /** Вызывается один раз на класс, перед всеми тестами  */
    void beforeClass(boolean isTest, String description);
    /** Вызывается один раз на инстанс = один раз на тестовый метод */
    void createdInstance(Object instance, Method method);
    void runningBefore (Object instance, Method method);
    void runningTest (Object instance, Method method);
    void runningAfter (Object instance, Method method);
    void testIsOk (Object instance, Method method);
    void testIsFail (Object instance, Method method, String message);
    void testThrewException (Object instance, Method method, Throwable e);
}
