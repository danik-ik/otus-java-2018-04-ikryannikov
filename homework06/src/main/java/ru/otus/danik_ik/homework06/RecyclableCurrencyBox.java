package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.DepositOneDenominationCurrencyBox;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.BundleFactory;
import ru.otus.danik_ik.homework06.money.Denomination;

import static java.lang.Integer.min;

public class RecyclableCurrencyBox extends BaseCurrencyBox implements DepositOneDenominationCurrencyBox {

    public RecyclableCurrencyBox(Denomination denomination, int capacity, int count, BundleFactory bundleFactory) {
        super(denomination, capacity, count, bundleFactory);
    }

    public RecyclableCurrencyBox(Denomination denomination, int capacity, int count) {
        super(denomination, capacity, count);
    }

    @Override
    public void deposit(Bundle bundle) throws CantDepositException {
        if (bundle.getCount() > canToDeposit())
            throw new CantDepositException( String.format("Недостаточно свобобного места в кассете. " +
                            "Вносится: %d; может быть внесено: %d", bundle.getCount(), canToDeposit()
                    ));
        for (Banknote note: bundle) if (!denomination.equals(note.getDenomination()))
            throw new CantDepositException(String.format("Несответствующий номинал: %s",
                    note.getDenomination().name()));
        count += bundle.getCount();
    }

    @Override
    public int canToDeposit(int count) {
        return min(count, canToDeposit());

    }

    private int canToDeposit() { return capacity - this.count; }

    @Override
    public boolean acceptsDenomination(Denomination denomination) {
        return this.denomination.equals(denomination);
    }
}
