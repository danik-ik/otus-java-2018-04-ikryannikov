package ru.otus.danik_ik.homework06;

import org.junit.Test;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class RecyclableCurrencyBoxTest {
    private final Denomination denom = Denomination.TWO_THOUSAND;

    @Test
    public void DepositToEmpty() throws CantDepositException {
        RecyclableCurrencyBox it = new RecyclableCurrencyBox(denom, 3000, 0);

        it.deposit(Bundle.byCount(denom, 20));
        assertEquals(20, it.getCount());
    }

    @Test
    public void DepositToNonEmpty() throws CantDepositException {
        RecyclableCurrencyBox it = new RecyclableCurrencyBox(denom, 3000, 2980);

        it.deposit(Bundle.byCount(denom, 20));
        assertEquals(3000, it.getCount());
    }

    @Test(expected = CantDepositException.class)
    public void DepositOverflow() throws CantDepositException {
        RecyclableCurrencyBox it = new RecyclableCurrencyBox(denom, 3000, 2980);

        it.deposit(Bundle.byCount(denom, 21));
    }

    @Test(expected = CantDepositException.class)
    public void DepositOtherDenomination() throws CantDepositException {
        RecyclableCurrencyBox it = new RecyclableCurrencyBox(denom, 3000, 2980);

        it.deposit(Bundle.byCount(Denomination.FIVE_THOUSAND, 1));
    }

    @Test
    public void CanToDepositEnough() {
        RecyclableCurrencyBox it = new RecyclableCurrencyBox(denom, 3000, 20);

        assertEquals(100, it.canToDeposit(100));
    }

    @Test
    public void CanToDepositNotEnough() {
        RecyclableCurrencyBox it = new RecyclableCurrencyBox(denom, 3000, 2980);

        assertEquals(20, it.canToDeposit(100));
    }
}
