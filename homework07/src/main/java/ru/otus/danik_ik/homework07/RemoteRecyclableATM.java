package ru.otus.danik_ik.homework07;

import ru.otus.danik_ik.homework06.RecyclableATM;
import ru.otus.danik_ik.homework06.atm.DepositAllDenominationsCurrencyBox;
import ru.otus.danik_ik.homework06.atm.RecyclableCurrencyBox;
import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;
import ru.otus.danik_ik.homework06.money.BundleFactory;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

public class RemoteRecyclableATM implements RemoteAtm {
    private final RecyclableATM atm;
    private final String name;
    private final BiConsumer<RemoteAtm, String> callbackHandler;

    public RemoteRecyclableATM(BundleFactory bundleFactory, String name, BiConsumer<RemoteAtm, String> callbackHandler) {
        atm = new RecyclableATM(bundleFactory);
        this.name = name;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public BoxSet replaceCurrencyBoxes(BoxSet boxSet) {
        callbackHandler.accept(this, "замена кассет");
        int inBoxesCount = atm.getWithdrawBoxCount();
        WithdrawCurrencyBox[] outBoxes = 
                new WithdrawCurrencyBox[ Math.max(boxSet.getWithdrawBoxCount(), inBoxesCount) ];
        
        // Что не влезает или не подходит по типу остаётся снаружи
        for (int i = 0; i < outBoxes.length; i++) {
            outBoxes[i] = boxSet.getWithdrawBox(i);
            if (i < inBoxesCount && outBoxes[i] instanceof RecyclableCurrencyBox)
                outBoxes[i] = atm.replaceRecyclableBox(i, (RecyclableCurrencyBox)outBoxes[i]);
        }
        DepositAllDenominationsCurrencyBox dBox = atm.replaceDepositBox(boxSet.getDepositBox());
                
        return new BoxSet(dBox, outBoxes);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getAmountToIssue() {
        callbackHandler.accept(this, "Запрашивается сумма к выдаче");
        return atm.getAmountToIssue();
    }

    @Override
    public BigDecimal getAmountTotal() {
        callbackHandler.accept(this, "Запрашивается полная сумма");
        return atm.getAmountTotal();
    }

    RecyclableATM getATM() {
        return atm;
    }
}
