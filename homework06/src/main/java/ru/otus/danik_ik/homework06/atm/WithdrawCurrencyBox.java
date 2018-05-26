package ru.otus.danik_ik.homework06.atm;

import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;
import ru.otus.danik_ik.homework06.money.Bundle;

import java.util.List;

public interface WithdrawCurrencyBox extends CurrencyBox {
    public Bundle withdraw(int count) throws NotEnoughException;
    public int canToWithdraw(int count);
}
