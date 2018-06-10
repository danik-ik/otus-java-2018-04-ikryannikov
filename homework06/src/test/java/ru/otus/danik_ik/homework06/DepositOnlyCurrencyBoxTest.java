package ru.otus.danik_ik.homework06;

import org.junit.Test;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.BundleFactory;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class DepositOnlyCurrencyBoxTest {
    private DepositOnlyCurrencyBox it = new DepositOnlyCurrencyBox(100);
    private BundleFactory bundleFactory = new DefaultBundleFactory();

    @Test
    public void getAmountEmpty() {
        DepositOnlyCurrencyBox it = new DepositOnlyCurrencyBox(100);
        assertEquals(BigDecimal.valueOf(0), it.getAmount());
    }

    @Test
    public void getAmountOneDenomination() throws Exception {
        it.deposit(get5000());
        assertEquals(BigDecimal.valueOf(5000), it.getAmount());
    }

    @Test
    public void getAmountOneDenomination2() throws Exception {
        it.deposit(get400());
        assertEquals(BigDecimal.valueOf(400), it.getAmount());
    }

    @Test
    public void getAmountMixedDenominations() throws Exception {
        it.deposit(get5400());
        assertEquals(BigDecimal.valueOf(5400), it.getAmount());
    }

    @Test
    public void getCountMixedDenominations() throws Exception {
        it.deposit(get5400());
        assertEquals(3, it.getCount());
    }

    @Test
    public void getCountEmpty() throws Exception {
        assertEquals(0, it.getCount());
    }

    @Test
    public void canToDeposit() {
        assertEquals(20, it.canToDeposit(20));
    }

    @Test
    public void canToDepositTooMany() {
        assertEquals(100, it.canToDeposit(200));
    }

    private Bundle get5000() throws Exception {
        return bundleFactory.byValues(5000);
    }

    private Bundle get400() throws Exception {
        return bundleFactory.byValues(200, 200);
    }

    private Bundle get5400() throws Exception {
        return bundleFactory.byValues(5000, 200, 200);
    }
}
