package ru.otus.danik_ik.homework07;

import ru.otus.danik_ik.homework06.RecyclableATM;
import ru.otus.danik_ik.homework06.atm.DepositAllDenominationsCurrencyBox;
import ru.otus.danik_ik.homework06.atm.RecyclableCurrencyBox;
import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;
import ru.otus.danik_ik.homework06.money.BundleFactory;

import java.util.function.BiConsumer;

public class RemoteRecyclableATM extends RecyclableATM implements RemoteAtm {
    private final String name;
    private final BiConsumer<RemoteAtm, String> callbackHandler;

    public RemoteRecyclableATM(BundleFactory bundleFactory, String name, BiConsumer<RemoteAtm, String> callbackHandler) {
        super(bundleFactory);
        this.name = name;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public BoxSet replaceCurrencyBoxes(BoxSet boxSet) {
        int inBoxesCount = this.getWithdrawBoxCount(); 
        WithdrawCurrencyBox[] outBoxes = 
                new WithdrawCurrencyBox[ Math.max(boxSet.getWithdrawBoxCount(), inBoxesCount) ];
        
        // Что не влезает или не подходит по типу остаётся снаружи
        for (int i = 0; i < outBoxes.length; i++) {
            outBoxes[i] = boxSet.getWithdrawBox(i);
            if (i < inBoxesCount && outBoxes[i] instanceof RecyclableCurrencyBox)
                outBoxes[i] = this.replaceRecyclableBox(i, (RecyclableCurrencyBox)outBoxes[i]);
        }
        DepositAllDenominationsCurrencyBox dBox = replaceDepositBox(boxSet.getDepositBox());
                
        return new BoxSet(dBox, outBoxes);
    }

    @Override
    public String getName() {
        return name;
    }

    // TODO: 10.06.2018 обернуть унаследованные методы. Вызывать коллбеки с комментариями, что таки происходит.
}
