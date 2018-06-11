package ru.otus.danik_ik.homework07;

import java.math.BigDecimal;

public interface RemoteAtm {
    BoxSet replaceCurrencyBoxes(BoxSet boxSet);
    BigDecimal getAmountToIssue();
    BigDecimal getAmountTotal();
    String getName();
}
