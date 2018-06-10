package ru.otus.danik_ik.homework06.atm;

import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.Denomination;

public interface WithdrawCurrencyBox extends CurrencyBox {
    Denomination getDenomination();
    Bundle withdraw(int count) throws NotEnoughException;
    int canToWithdraw(int count);
}
