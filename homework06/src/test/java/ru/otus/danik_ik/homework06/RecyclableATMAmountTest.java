package ru.otus.danik_ik.homework06;

import org.junit.Test;
import ru.otus.danik_ik.homework06.atm.ATM;
import ru.otus.danik_ik.homework06.atm.DepositAllDenominationsCurrencyBox;
import ru.otus.danik_ik.homework06.atm.RecyclableCurrencyBox;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.BundleFactory;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class RecyclableATMAmountTest {

    private BundleFactory bundleFactory = new DefaultBundleFactory();

    @Test
    public void EmptyATMGetAmountToIssue() {
        ATM atm = new RecyclableATM(bundleFactory);
        assertEquals(BigDecimal.valueOf(0), atm.getAmountToIssue());
    }

    @Test
    public void EmptyATMGetAmountTotal() {
        ATM atm = new RecyclableATM(bundleFactory);
        assertEquals(BigDecimal.valueOf(0), atm.getAmountTotal());
    }

    @Test
    public void ATMGetAmountTotal() {
        RecyclableATM atm = new RecyclableATM(bundleFactory);

        atm.replaceRecyclableBox(1, new DummyBox(200));
        atm.replaceRecyclableBox(2, new DummyBox(400));
        atm.replaceDepositBox(new DummyBox(800));

        assertEquals(BigDecimal.valueOf(1400), atm.getAmountTotal());
    }

    @Test
    public void ATMGetAmountToIssue() {
        RecyclableATM atm = new RecyclableATM(bundleFactory);

        atm.replaceRecyclableBox(1, new DummyBox(200));
        atm.replaceRecyclableBox(2, new DummyBox(400));
        atm.replaceDepositBox(new DummyBox(800));

        assertEquals(BigDecimal.valueOf(600), atm.getAmountToIssue());
    }

    private class DummyBox implements RecyclableCurrencyBox, DepositAllDenominationsCurrencyBox {
        private int amount;

        public DummyBox(int amount) {
            this.amount = amount;
        }

        // Эмулируется
        @Override
        public BigDecimal getAmount() {
            return BigDecimal.valueOf(amount);
        }

        // не используются
        @Override public boolean acceptsDenomination(Denomination denomination) { return false; }
        @Override public void deposit(Bundle bundle) throws CantDepositException {}
        @Override public int canToDeposit(int count) { return 0; }
        @Override public Denomination getDenomination() { return null; }
        @Override public Bundle withdraw(int count) throws NotEnoughException { return null; }
        @Override public int canToWithdraw(int count) { return 0; }
        @Override public int getCount() { return 0; }
    }
}
