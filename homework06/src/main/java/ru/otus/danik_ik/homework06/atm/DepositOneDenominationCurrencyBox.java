package ru.otus.danik_ik.homework06.atm;

import ru.otus.danik_ik.homework06.money.Denomination;

public interface DepositOneDenominationCurrencyBox extends DepositCurrencyBox {
    boolean acceptsDenomination(Denomination denomination);
}
