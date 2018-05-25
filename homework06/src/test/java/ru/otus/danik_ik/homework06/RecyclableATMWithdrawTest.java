package ru.otus.danik_ik.homework06;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.atm.exceptions.AmountCantBeCollectedException;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static ru.otus.danik_ik.homework06.money.Denomination.*;

@RunWith(Parameterized.class)
public class RecyclableATMWithdrawTest {
    BigDecimal amount;
    List<Banknote> bundle;

    public RecyclableATMWithdrawTest(int amount, Denomination[] notes) {
        this.amount = BigDecimal.valueOf(amount);
        bundle = Stream.of(notes)
                .map(Banknote::new)
                .collect(Collectors.toList());
        bundle.sort(Comparator.comparing(a -> ((Banknote)a).getDenomination().asInt()).reversed());
        insertBoxes();
    }

    RecyclableATM atm = new RecyclableATM();
    RecyclableCurrencyBox[] rBox = new RecyclableCurrencyBox[4];
    DepositCurrencyBox dBox;

    private void insertBoxes() {
        Denomination[] denoms = new Denomination[]{ONE_HUNDRED, FIVE_HUNDRED, ONE_THOUSAND, FIVE_THOUSAND};
        for (int i = 0; i < 4; i++) {
            rBox[i] = new RecyclableCurrencyBox(denoms[i], 100, 95);
            atm.replaceRecyclableBox(i, rBox[i]);
        }
        atm.replaceDepositBox(new DepositOnlyCurrencyBox(300));
    }

    @Parameterized.Parameters(name = "{index}: («{0}»)")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
                {7900, new Denomination[]{
                        FIVE_THOUSAND,
                        ONE_THOUSAND,
                        ONE_THOUSAND,
                        FIVE_HUNDRED,
                        ONE_HUNDRED,
                        ONE_HUNDRED,
                        ONE_HUNDRED,
                        ONE_HUNDRED,
                }},
        });
    }

    @Test
    public void withdraw() throws AmountCantBeCollectedException {
        List<Banknote> bundle = atm.withdraw(amount);
        assertNotNull(bundle);

        bundle.sort(Comparator.comparing(a -> ((Banknote)a).getDenomination().asInt()).reversed());
        assertEquals(this.bundle.size(), bundle.size());
        for (int i = 0; i < bundle.size(); i++)
            assertEquals(this.bundle.get(i).getDenomination(), bundle.get(i).getDenomination());
    }

}
