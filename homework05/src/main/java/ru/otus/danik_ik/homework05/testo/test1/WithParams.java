package ru.otus.danik_ik.homework05.testo.test1;

import ru.otus.danik_ik.homework05.testo.annotations.Test;

public class WithParams {
    @Test
    public void test(Object o) throws Exception {
        throw new Exception("Тест c параметрами вызываться не должен");
    }
}
