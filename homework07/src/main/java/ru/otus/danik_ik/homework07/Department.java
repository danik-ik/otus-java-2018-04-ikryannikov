package ru.otus.danik_ik.homework07;

import java.math.BigDecimal;
import java.util.Map;

public interface Department {
    Department subcsribe(RemoteAtm atm, BoxSet boxes);
    Department unsubcsribe(RemoteAtm atm);
    Map<RemoteAtm, BoxSet> replaceBoxes();
    Map<RemoteAtm, BigDecimal> getAmountsTotal();
    Map<RemoteAtm, BigDecimal> getAmountsToIssue();
}
