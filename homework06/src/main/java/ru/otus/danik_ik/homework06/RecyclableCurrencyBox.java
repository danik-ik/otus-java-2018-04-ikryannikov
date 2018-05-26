package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.Denomination;

import static java.lang.Integer.min;

public class RecyclableCurrencyBox extends BaseCurrencyBox implements DepositCurrencyBox {
    public RecyclableCurrencyBox(Denomination denomination, int capacity, int count) {
        super(denomination, capacity, count);
    }

    @Override
    public void deposit(Bundle bundle) throws CantDepositException {
        if (bundle.size() > capacity - count) throw new CantDepositException("Нет места в кассете");
        for (Banknote note: bundle) if (!denomination.equals(note.getDenomination()))
            throw new CantDepositException("Несответствующий номинал");
        count += bundle.size();
    }

    @Override
    public int canToDeposit(int count) {
        return min(count, capacity - this.count);

    }

    @Override
    public boolean acceptsDenomination(Denomination denomination) {
        return this.denomination.equals(denomination);
    }
}
