package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.ATM;
import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;
import ru.otus.danik_ik.homework06.atm.exceptions.AmountCantBeCollectedException;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Banknote;

import java.math.BigDecimal;
import java.util.List;

public class RecyclableATM implements ATM {
    RecyclableCurrencyBox[] currencyBoxes = new RecyclableCurrencyBox[4];
    DepositCurrencyBox depositBox;

    public DepositCurrencyBox replaceDepositBox(DepositCurrencyBox currencyBox){
        DepositCurrencyBox result = depositBox;
        depositBox = currencyBox;
        return result;
    }

    public RecyclableCurrencyBox replaceRecyclableBox(int slot, RecyclableCurrencyBox currencyBox){
        RecyclableCurrencyBox result = currencyBoxes[slot];
        currencyBoxes[slot] = currencyBox;
        return result;
    }

    @Override
    public void deposit(List<Banknote> bundle) throws CantDepositException {

    }

    @Override
    public List<Banknote> withdraw(BigDecimal amount) throws AmountCantBeCollectedException {
        return null;
    }

    @Override
    public BigDecimal getAmountToIssue() {
        BigDecimal result = new BigDecimal(0);
        for (RecyclableCurrencyBox box: currencyBoxes)
            if (box != null)
                result = result.add(box.getAmount());
        return result;
    }

    @Override
    public BigDecimal getAmountTotal() {
        return depositBox == null?
                getAmountToIssue() :
                getAmountToIssue().add(depositBox.getAmount());
    }
}
