package ru.otus.danik_ik.homework07;

import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;

public class BoxSet {
    private final DepositCurrencyBox depositBox;
    private final WithdrawCurrencyBox[] withdrawBoxes;

    public BoxSet(DepositCurrencyBox inputBox, WithdrawCurrencyBox... withdrawBoxes) {
        this.depositBox = inputBox;
        this.withdrawBoxes = withdrawBoxes;
    }

    public DepositCurrencyBox getDepositBox() {
        return depositBox;
    }

    public int getWithdrawBoxCount() { return withdrawBoxes.length; }
    
    public WithdrawCurrencyBox getWithdrawBox(int index) {
        return withdrawBoxes[index];
    }
}
