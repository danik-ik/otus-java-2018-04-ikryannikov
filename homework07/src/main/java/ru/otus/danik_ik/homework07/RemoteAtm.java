package ru.otus.danik_ik.homework07;

import java.math.BigDecimal;

public interface RemoteAtm {
    BoxSet replaceCurrencyBoxes(BoxSet set);
    BigDecimal getAmountToIssue();
    BigDecimal getAmountTotal();
}
