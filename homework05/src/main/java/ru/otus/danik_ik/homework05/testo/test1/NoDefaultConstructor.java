package ru.otus.danik_ik.homework05.testo.test1;

import ru.otus.danik_ik.homework05.testo.annotations.Test;

public class NoDefaultConstructor {
    public NoDefaultConstructor(Object object) {};

    @Test
    public void test() throws Exception {
        throw new Exception("Тест без конструктора по умолчанию вызываться не должен");
    }
}
