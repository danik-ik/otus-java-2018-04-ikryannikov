package ru.otus.danik_ik.homework06.atm;

import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;
import ru.otus.danik_ik.homework06.money.Banknote;

import java.util.List;

public interface WithdrawCurrencyBox extends CurrencyBox {
    public List<Banknote> withdraw(int count) throws NotEnoughException;
}
