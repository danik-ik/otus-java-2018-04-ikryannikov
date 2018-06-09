package ru.otus.danik_ik.homework06.atm;

import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.Denomination;

public interface DepositCurrencyBox extends CurrencyBox {
    void deposit(Bundle bundle) throws CantDepositException;
    int canToDeposit(int count);
    boolean acceptsDenomination(Denomination denomination);
}
