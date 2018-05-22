package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.ATM;
import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;
import ru.otus.danik_ik.homework06.atm.exceptions.AmountCantBeCollectedException;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Banknote;

import java.math.BigDecimal;
import java.util.List;

public class SuperMegaATM2000 implements ATM {
    WithdrawCurrencyBox[] withdrawBoxes = new WithdrawCurrencyBox[4];
    DepositCurrencyBox depositBox;

    public WithdrawCurrencyBox replaceWithdrawBox(int slot, WithdrawCurrencyBox currencyBox){
        WithdrawCurrencyBox result = withdrawBoxes[slot];
        withdrawBoxes[slot] = currencyBox;
        return result;
    }

    public DepositCurrencyBox replaceDepositBox(int slot, DepositCurrencyBox currencyBox){
        DepositCurrencyBox result = depositBox;
        depositBox = currencyBox;
        return result;
    }

    @Override
    public void deposit(List<Banknote> bundle) throws CantDepositException {
        depositBox.deposit(bundle);
    }

    @Override
    public List<Banknote> withdraw(BigDecimal amount) throws AmountCantBeCollectedException {
        // TODO: 21.05.2018 <<<<
        return null;
    }

    @Override
    public BigDecimal getAmountToIssue() {
        BigDecimal result = new BigDecimal(0);
        for (WithdrawCurrencyBox box: withdrawBoxes) result = result.add(box.getAmount());
        return result;
    }

    @Override
    public BigDecimal getAmountTotal() {
        return getAmountToIssue().add(depositBox.getAmount());
    }
}
