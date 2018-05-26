package ru.otus.danik_ik.homework06;

import org.junit.Assert;
import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.util.Comparator;

import static org.junit.Assert.*;

class RecyclableATMTest {
    protected final int INIT_COUNT = 95;
    protected final int INIT_CAPACITY = 100;

    protected RecyclableATM atm = new RecyclableATM();
    protected RecyclableCurrencyBox[] rBoxes = new RecyclableCurrencyBox[atm.getWithdrawBoxCount()];
    protected DepositCurrencyBox dBox;

    protected void init(Integer... values) {
        if (values.length > rBoxes.length)
            throw new IllegalArgumentException(String.format("insertBoxes: не более %d аргументов",
                    rBoxes.length));

        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                rBoxes[i] = new RecyclableCurrencyBox(Denomination.of(values[i]), INIT_CAPACITY, INIT_COUNT);
                atm.replaceRecyclableBox(i, rBoxes[i]);
            }
        }
        dBox = new DepositOnlyCurrencyBox(INIT_CAPACITY);
        atm.replaceDepositBox(dBox);
    }

    protected void checkBundle(Bundle expected, Bundle actual){
        assertNotNull(actual);
        actual.sort(Comparator.comparing(a -> ((Banknote)a).getDenomination().asInt()).reversed());
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++)
            assertEquals(expected.get(i).getDenomination(), actual.get(i).getDenomination());
    }

    protected void checkRemainder(int dCount, Integer... rCounts) {
        assertEquals(dCount, dBox.getCount());

        if (rCounts.length > rBoxes.length)
            throw new IllegalArgumentException(String.format("insertBoxes: не более %d аргументов rCounts",
                    rBoxes.length));

        for (int i = 0; i < rCounts.length; i++) {
            assertTrue((rCounts[i] == null) == (rBoxes[i] == null) );
            if (rCounts[i] != null)
                assertEquals(rCounts[i].intValue(), rBoxes[i].getCount());
        }
        assertEquals(dCount, dBox.getCount());
    }

}
