package ru.otus.danik_ik.homework07;

import ru.otus.danik_ik.homework06.atm.DepositAllDenominationsCurrencyBox;
import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;

public class BoxSet {
    private final DepositAllDenominationsCurrencyBox depositBox;
    private final WithdrawCurrencyBox[] withdrawBoxes;

    public BoxSet(DepositAllDenominationsCurrencyBox inputBox, WithdrawCurrencyBox... withdrawBoxes) {
        this.depositBox = inputBox;
        this.withdrawBoxes = withdrawBoxes;
    }

    public DepositAllDenominationsCurrencyBox getDepositBox() {
        return depositBox;
    }

    public int getWithdrawBoxCount() { return withdrawBoxes.length; }
    
    public WithdrawCurrencyBox getWithdrawBox(int index) {
        if (index >= withdrawBoxes.length || index < 0) return null;
        return withdrawBoxes[index];
    }
}
