package ru.otus.danik_ik.homework06;

import org.junit.Test;
import ru.otus.danik_ik.homework06.atm.ATM;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.BundleFactory;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static ru.otus.danik_ik.homework06.money.Denomination.*;

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
        atm.replaceDepositBox(new RecyclableCurrencyBox(ONE_HUNDRED, 3000, 7));
        assertEquals(BigDecimal.valueOf(700), atm.getAmountTotal());
    }

    @Test
    public void ATMGetAmountToWithdraw() {
        RecyclableATM atm = new RecyclableATM(bundleFactory);
        atm.replaceRecyclableBox(1, new RecyclableCurrencyBox(Denomination.TWO_HUNDRED, 3000, 6));
        atm.replaceDepositBox(new RecyclableCurrencyBox(ONE_HUNDRED, 3000, 7));
        assertEquals(BigDecimal.valueOf(1900), atm.getAmountTotal());
    }

}
