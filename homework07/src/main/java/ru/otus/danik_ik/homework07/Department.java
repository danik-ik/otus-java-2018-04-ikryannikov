package ru.otus.danik_ik.homework07;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Supplier;

public interface Department {
    Department register(RemoteAtm atm, Supplier<BoxSet> boxSetSupplier);
    Department unregister(RemoteAtm atm);
    Map<RemoteAtm, BoxSet> replaceBoxes();
    Map<RemoteAtm, BigDecimal> getAmountsTotal();
    Map<RemoteAtm, BigDecimal> getAmountsToIssue();
}
