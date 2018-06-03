package ru.otus.danik_ik.homework06;

import org.junit.Test;
import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BundleTest {
    @Test
    public void splitByDenominations() {
        Bundle it = Bundle.byValues(1000, 100, 1000, 5000, 5000, 1000);
        Map<Denomination, Bundle> map = it.splitByDenominations();
        assertEquals(3, map.size());
        assertEquals(1, map.get(Denomination.ONE_HUNDRED).getCount());
        assertEquals(3, map.get(Denomination.ONE_THOUSAND).getCount());
        assertEquals(2, map.get(Denomination.FIVE_THOUSAND).getCount());
        assertNull(map.get(Denomination.FIVE_HUNDRED));
    }

    @Test
    public void extract() throws NotEnoughException {
        Bundle from = Bundle.byValues(100, 200, 500);
        Bundle to = from.extract(2);
        assertEquals(1, from.getCount());
        assertEquals(2, to.getCount());
        assertEquals(Denomination.ONE_HUNDRED, to.asList().get(0).getDenomination());
        assertEquals(Denomination.TWO_HUNDRED, to.asList().get(1).getDenomination());
        assertEquals(Denomination.FIVE_HUNDRED, from.asList().get(0).getDenomination());
    }
}
