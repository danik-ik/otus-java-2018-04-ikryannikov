package ru.otus.danik_ik.homework07;

import java.math.BigDecimal;

public interface RemoteAtm {
    BoxSet replace(BoxSet set);
    public BigDecimal getAmountToIssue();
    public BigDecimal getAmountTotal();

}
