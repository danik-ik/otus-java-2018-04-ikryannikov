package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.util.List;

import static java.lang.Integer.min;

public class RecyclableCurrencyBox extends BaseCurrencyBox implements DepositCurrencyBox {
    public RecyclableCurrencyBox(Denomination denomination, int capacity, int count) {
        super(denomination, capacity, count);
    }

    @Override
    public void deposit(List<Banknote> notes) throws CantDepositException {
        if (notes.size() > capacity - count) throw new CantDepositException("Нет места в кассете");
        for (Banknote note: notes) if (!denomination.equals(note.getDenomination()))
            throw new CantDepositException("Несответствующий номинал");
        count += notes.size();
    }

    @Override
    public int canToDeposit(int count) {
        return min(count, capacity - this.count);

    }
}
