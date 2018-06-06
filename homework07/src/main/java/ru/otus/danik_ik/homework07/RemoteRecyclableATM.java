package ru.otus.danik_ik.homework07;

import ru.otus.danik_ik.homework06.RecyclableATM;
import ru.otus.danik_ik.homework06.RecyclableCurrencyBox;
import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;

public class RemoteRecyclableATM extends RecyclableATM implements RemoteAtm {
    @Override
    public BoxSet replaceCurrencyBoxes(BoxSet set) {
        int inBoxesCount = this.getWithdrawBoxCount(); 
        WithdrawCurrencyBox[] outBoxes = 
                new WithdrawCurrencyBox[ Math.max(set.getWithdrawBoxCount(), inBoxesCount) ];
        
        // Что не влезает или не подходит по типу остаётся снаружи
        for (int i = 0; i < outBoxes.length; i++) {
            outBoxes[i] = set.getWithdrawBox(i);
            if (i < inBoxesCount && outBoxes[i] instanceof RecyclableCurrencyBox) 
                outBoxes[i] = this.replaceRecyclableBox(i, (RecyclableCurrencyBox)outBoxes[i]);
        }
        DepositCurrencyBox dBox = replaceDepositBox(set.getDepositBox());
                
        return new BoxSet(dBox, outBoxes);
    }
}
