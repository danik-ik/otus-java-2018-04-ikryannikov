package ru.otus.danik_ik.homework06.atm;

import ru.otus.danik_ik.homework06.atm.exceptions.AmountCantBeCollectedException;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Banknote;

import java.math.BigDecimal;
import java.util.List;

public interface ATM {
    public void deposit(List<Banknote> bundle) throws CantDepositException;
    public List<Banknote> withdraw(BigDecimal amount) throws AmountCantBeCollectedException;
    public BigDecimal getAmountToIssue();
    public BigDecimal getAmountTotal();
}
