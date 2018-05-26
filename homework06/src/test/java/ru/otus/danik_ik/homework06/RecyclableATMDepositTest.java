package ru.otus.danik_ik.homework06;

import org.junit.Test;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Bundle;

public class RecyclableATMDepositTest extends RecyclableATMTest{
    @Test
    public void withoutOverflow() throws CantDepositException {
        init(5000, 1000, 500, 100);
        atm.deposit(Bundle.byValues(5000, 100, 5000, 500, 500, 500));
        checkRemainder(
                0,
                INIT_COUNT + 2, // 5000
                INIT_COUNT + 0, // 1000
                INIT_COUNT + 3, // 500
                INIT_COUNT + 1 // 100
        );
    }
}
