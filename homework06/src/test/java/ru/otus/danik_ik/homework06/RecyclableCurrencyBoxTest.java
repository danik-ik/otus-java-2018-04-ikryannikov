package ru.otus.danik_ik.homework06;

import org.junit.Test;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.BundleFactory;
import ru.otus.danik_ik.homework06.money.Denomination;

import static org.junit.Assert.assertEquals;

public class RecyclableCurrencyBoxTest {

    private final Denomination denom = Denomination.TWO_THOUSAND;
    private BundleFactory bundleFactory = new DefaultBundleFactory();

    @Test
    public void DepositToEmpty() throws CantDepositException {
        RecyclableCurrencyBoxImpl it = new RecyclableCurrencyBoxImpl(denom, 3000, 0);

        it.deposit(bundleFactory.byCount(denom, 20));
        assertEquals(20, it.getCount());
    }

    @Test
    public void DepositToNonEmpty() throws CantDepositException {
        RecyclableCurrencyBoxImpl it = new RecyclableCurrencyBoxImpl(denom, 3000, 2980);

        it.deposit(bundleFactory.byCount(denom, 20));
        assertEquals(3000, it.getCount());
    }

    @Test(expected = CantDepositException.class)
    public void DepositOverflow() throws CantDepositException {
        RecyclableCurrencyBoxImpl it = new RecyclableCurrencyBoxImpl(denom, 3000, 2980);

        it.deposit(bundleFactory.byCount(denom, 21));
    }

    @Test(expected = CantDepositException.class)
    public void DepositOtherDenomination() throws CantDepositException {
        RecyclableCurrencyBoxImpl it = new RecyclableCurrencyBoxImpl(denom, 3000, 2980);

        it.deposit(bundleFactory.byCount(Denomination.FIVE_THOUSAND, 1));
    }

    @Test
    public void CanToDepositEnough() {
        RecyclableCurrencyBoxImpl it = new RecyclableCurrencyBoxImpl(denom, 3000, 20);

        assertEquals(100, it.canToDeposit(100));
    }

    @Test
    public void CanToDepositNotEnough() {
        RecyclableCurrencyBoxImpl it = new RecyclableCurrencyBoxImpl(denom, 3000, 2980);

        assertEquals(20, it.canToDeposit(100));
    }
}
