package ru.otus.danik_ik.homework06;

import org.junit.Test;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.BundleFactory;
import ru.otus.danik_ik.homework06.money.Denomination;

public class RecyclableATMDepositTest extends RecyclableATMTest{

    private BundleFactory bundleFactory = new DefaultBundleFactory();

    @Test
    public void withoutOverflow() throws CantDepositException {
        init(5000, 1000, 500, 100);
        atm.deposit(bundleFactory.byValues(5000, 100, 5000, 500, 500, 500));
        checkRemainder(
                0,
                INIT_COUNT + 2, // 5000
                INIT_COUNT + 0, // 1000
                INIT_COUNT + 3, // 500
                INIT_COUNT + 1 // 100
        );
    }

    @Test
    public void withOverflow() throws CantDepositException {
        init(5000, 1000, 500, 100);
        Bundle bundle = bundleFactory.empty();
        bundle.addAll(bundleFactory.byCount(Denomination.ONE_HUNDRED, INIT_CAPACITY));
        atm.deposit(bundle);
        checkRemainder(
                INIT_COUNT, // что не поместилось в ячейку ( == INIT_CAPACITY - (INIT_CAPACITY - INIT_COUNT) )
                INIT_COUNT + 0, // 5000
                INIT_COUNT + 0, // 1000
                INIT_COUNT + 0, // 500
                INIT_CAPACITY // 100
        );
    }

    @Test
    public void withOverflow2() throws CantDepositException {
        init(5000, 1000, 500, 100);
        Bundle bundle = bundleFactory.empty();
        bundle.addAll(bundleFactory.byCount(Denomination.ONE_HUNDRED, INIT_CAPACITY - INIT_COUNT + 5));
        bundle.addAll(bundleFactory.byCount(Denomination.ONE_THOUSAND, INIT_CAPACITY - INIT_COUNT + 5));
        atm.deposit(bundle);
        checkRemainder(
                5 + 5,
                INIT_COUNT + 0, // 5000
                INIT_CAPACITY, // 1000
                INIT_COUNT + 0, // 500
                INIT_CAPACITY // 100
        );
    }

    @Test
    public void unsupportedToUniversalBox() throws CantDepositException {
        init(5000, 1000, 500, 100);
        Bundle bundle = bundleFactory.empty();
        bundle.addAll(bundleFactory.byCount(Denomination.TWO_THOUSAND, INIT_COUNT));
        atm.deposit(bundle);
        checkRemainder(
                INIT_COUNT,
                INIT_COUNT + 0, // 5000
                INIT_COUNT + 0, // 1000
                INIT_COUNT + 0, // 500
                INIT_COUNT + 0 // 100
        );
    }

    @Test (expected = CantDepositException.class)
    public void tooMany() throws CantDepositException {
        init(5000, 1000, 500, 100);
        Bundle bundle = bundleFactory.empty();
        bundle.addAll(bundleFactory.byCount(Denomination.ONE_THOUSAND, INIT_CAPACITY + INIT_CAPACITY));
        atm.deposit(bundle);
        checkRemainder(
                INIT_COUNT,
                INIT_COUNT + 0, // 5000
                INIT_COUNT + 0, // 1000
                INIT_COUNT + 0, // 500
                INIT_COUNT + 0 // 100
        );
    }

    @Test (expected = CantDepositException.class)
    public void nonUniversalBox() throws Exception {
        init(5000, 1000, 500, 100);
        atm.replaceDepositBox(new RecyclableCurrencyBox(Denomination.ONE_HUNDRED, INIT_CAPACITY, 0));
        Bundle bundle = bundleFactory.empty();
        bundle.addAll(bundleFactory.byCount(Denomination.TWO_THOUSAND, INIT_COUNT));
        atm.deposit(bundle);
    }
}
