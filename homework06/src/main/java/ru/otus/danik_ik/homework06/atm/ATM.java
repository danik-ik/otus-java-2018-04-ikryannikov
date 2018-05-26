package ru.otus.danik_ik.homework06.atm;

import ru.otus.danik_ik.homework06.atm.exceptions.AmountCantBeCollectedException;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Bundle;

import java.math.BigDecimal;

public interface ATM {
    public void deposit(Bundle bundle) throws CantDepositException;
    public Bundle withdraw(BigDecimal amount) throws AmountCantBeCollectedException;
    public BigDecimal getAmountToIssue();
    public BigDecimal getAmountTotal();
}
