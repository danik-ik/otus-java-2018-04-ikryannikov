package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

class RecyclableATMTest {

    protected RecyclableATM atm = new RecyclableATM();
    protected RecyclableCurrencyBox[] rBoxes = new RecyclableCurrencyBox[atm.getWithdrawBoxCount()];
    protected DepositCurrencyBox dBox;

    protected void init(Integer... values) {
        if (values.length > rBoxes.length)
            throw new IllegalArgumentException(String.format("insertBoxes: не более %d аргументов",
                    rBoxes.length));

        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                rBoxes[i] = new RecyclableCurrencyBox(Denomination.of(values[i]), 100, 95);
                atm.replaceRecyclableBox(i, rBoxes[i]);
            }
        }
        dBox = new DepositOnlyCurrencyBox(300);
        atm.replaceDepositBox(dBox);
    }

    protected void checkBundle(Bundle expected, Bundle actual){
        assertNotNull(actual);
        actual.sort(Comparator.comparing(a -> ((Banknote)a).getDenomination().asInt()).reversed());
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++)
            assertEquals(expected.get(i).getDenomination(), actual.get(i).getDenomination());
    }

}
