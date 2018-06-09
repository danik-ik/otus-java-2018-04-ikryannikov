package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.BundleFactory;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

class RecyclableATMTest {
    protected final int INIT_COUNT = 95;
    protected final int INIT_CAPACITY = 100;

    protected RecyclableATM atm = new RecyclableATM(BundleFactory.getDefault());
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
        assertEquals(expected.getCount(), actual.getCount());

        List<Banknote> expectedList = asSortedList(expected);
        List<Banknote> actualList = asSortedList(actual);

        for (int i = 0; i < actualList.size(); i++)
            assertEquals(expectedList.get(i).getDenomination(), actualList.get(i).getDenomination());
    }

    private List<Banknote> asSortedList(Bundle source) {
        List<Banknote> result = new ArrayList<>();
        source.forEach(result::add);
        result.sort(Banknote.getReversedDenominationComparator());
        return result;
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
