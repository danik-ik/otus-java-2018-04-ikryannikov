package ru.otus.danik_ik.homework06.atm;

import ru.otus.danik_ik.homework06.money.Denomination;

import java.math.BigDecimal;

public interface CurrencyBox {
    int getCount();
    BigDecimal getAmount();
}
