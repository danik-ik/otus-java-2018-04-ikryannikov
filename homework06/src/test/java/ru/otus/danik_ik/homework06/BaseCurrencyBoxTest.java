package ru.otus.danik_ik.homework06;

import org.junit.Test;
import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class BaseCurrencyBoxTest {
    private final Denomination denomination = Denomination.TWO_THOUSAND;

    @Test
    public void getDenominationTest() {
        BaseCurrencyBox it = new BaseCurrencyBox(denomination, 3000, 0);
        assertEquals(Denomination.TWO_THOUSAND, it.getDenomination());
    }

    @Test
    public void canToWithdrawEnough() {
        BaseCurrencyBox it = new BaseCurrencyBox(denomination, 3000, 2000);
        assertEquals(20, it.canToWithdraw(20));
    }

    @Test
    public void canToWithdrawNotEnough() {
        BaseCurrencyBox it = new BaseCurrencyBox(denomination, 3000, 21);
        assertEquals(21, it.canToWithdraw(200));
    }

    @Test
    public void getAmount() {
        BaseCurrencyBox it = new BaseCurrencyBox(Denomination.TWO_HUNDRED, 3000, 21);
        assertEquals(BigDecimal.valueOf(21 * 200), it.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawNonPositiveCount() throws NotEnoughException {
        BaseCurrencyBox it = new BaseCurrencyBox(denomination, 3000, 21);
        it.withdraw(0);
    }

    @Test(expected = NotEnoughException.class)
    public void withdrawNotEnough() throws NotEnoughException {
        BaseCurrencyBox it = new BaseCurrencyBox(denomination, 3000, 21);
        it.withdraw(22);
    }

    @Test
    public void withdrawEnough() throws NotEnoughException {
        BaseCurrencyBox it = new BaseCurrencyBox(Denomination.TWO_HUNDRED, 3000, 21);
        Bundle bundle = it.withdraw(3);

        assertEquals(3, bundle.size());
        for (Banknote note: bundle)
            assertEquals(note.getDenomination(), it.getDenomination());
    }

    @Test
    public void withdrawDecreasesCount() throws NotEnoughException {
        BaseCurrencyBox it = new BaseCurrencyBox(Denomination.TWO_HUNDRED, 3000, 21);
        it.withdraw(3);

        assertEquals(18, it.getCount());
    }

}
