package ru.otus.danik_ik.homework06.atm;

import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Banknote;

import java.util.List;

public interface DepositCurrencyBox extends CurrencyBox {
    public void deposit(List<Banknote> notes) throws CantDepositException;
    public int canToDeposit(int count);
}
