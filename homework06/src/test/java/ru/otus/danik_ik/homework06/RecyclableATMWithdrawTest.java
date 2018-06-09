package ru.otus.danik_ik.homework06;

import org.junit.Test;
import ru.otus.danik_ik.homework06.atm.exceptions.AmountCantBeCollectedException;
import ru.otus.danik_ik.homework06.money.BundleFactory;

import java.math.BigDecimal;

public class RecyclableATMWithdrawTest extends RecyclableATMTest{

    private BundleFactory bundleFactory = new DefaultBundleFactory();

    @Test
    public void withdraw7900() throws AmountCantBeCollectedException {
        init(5000, 100, 1000, 500);
        checkBundle(
                bundleFactory.byValues(5000, 1000, 1000, 500, 100, 100, 100, 100),
                atm.withdraw(new BigDecimal(7900))
        );
        checkRemainder(
                0, // внесено
                INIT_COUNT - 1, //5000
                INIT_COUNT - 4, // 100
                INIT_COUNT - 2, // 1000
                INIT_COUNT - 1 // 500
        );
    }

    @Test
    public void withdraw1100() throws AmountCantBeCollectedException {
        init(100);
        checkBundle(
                bundleFactory.byValues(100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100),
                atm.withdraw(new BigDecimal(1100))
        );
        checkRemainder(
                0, // внесено
                INIT_COUNT - 11 // 100
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
