package ru.otus.danik_ik.homework06.atm;

import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.util.List;

public interface DepositCurrencyBox extends CurrencyBox {
    public void deposit(Bundle bundle) throws CantDepositException;
    public int canToDeposit(int count);
    public boolean acceptsDenomination(Denomination denomination);
}
