package ru.otus.danik_ik.homework06;

import org.junit.Test;
import ru.otus.danik_ik.homework06.atm.exceptions.AmountCantBeCollectedException;
import ru.otus.danik_ik.homework06.money.Bundle;

import java.math.BigDecimal;

public class RecyclableATMWithdrawTest extends RecyclableATMTest{
    @Test
    public void withdraw7900() throws AmountCantBeCollectedException {
        init(5000, 100, 1000, 500);
        checkBundle(
                Bundle.byValues(5000, 1000, 1000, 500, 100, 100, 100, 100),
                atm.withdraw(new BigDecimal(7900))
        );
    }

    @Test
    public void withdraw1100() throws AmountCantBeCollectedException {
        init(100);
        checkBundle(
                Bundle.byValues(100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100),
                atm.withdraw(new BigDecimal(1100))
        );
    }

    @Test(expected = AmountCantBeCollectedException.class)
    public void withdrawCannotCollect() throws AmountCantBeCollectedException {
        init(1000);
        atm.withdraw(new BigDecimal(1100));
    }

    @Test(expected = AmountCantBeCollectedException.class)
    public void withdrawNotEnough() throws AmountCantBeCollectedException {
        init(1000);
        atm.withdraw(new BigDecimal(100000));
    }
}
