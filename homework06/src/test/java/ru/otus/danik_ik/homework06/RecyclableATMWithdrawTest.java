package ru.otus.danik_ik.homework06;

import org.junit.Test;
import ru.otus.danik_ik.homework06.atm.exceptions.AmountCantBeCollectedException;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.math.BigDecimal;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RecyclableATMWithdrawTest {

    private RecyclableATM atm = new RecyclableATM();
    private RecyclableCurrencyBox[] rBox = new RecyclableCurrencyBox[atm.getWithdrawBoxCount()];

    private void init(Integer... values) {
        if (values.length > rBox.length)
            throw new IllegalArgumentException(String.format("insertBoxes: не более %d аргументов",
                    rBox.length));

        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                rBox[i] = new RecyclableCurrencyBox(Denomination.of(values[i]), 100, 95);
                atm.replaceRecyclableBox(i, rBox[i]);
            }
        }
        atm.replaceDepositBox(new DepositOnlyCurrencyBox(300));
    }

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

    private void checkBundle(Bundle expected, Bundle actual){
        assertNotNull(actual);
        actual.sort(Comparator.comparing(a -> ((Banknote)a).getDenomination().asInt()).reversed());
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++)
            assertEquals(expected.get(i).getDenomination(), actual.get(i).getDenomination());
    }

}
