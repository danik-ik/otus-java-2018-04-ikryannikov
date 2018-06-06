package ru.otus.danik_ik.homework07;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SimpleDepartment implements Department {
    private Map<RemoteAtm, BoxSet> atms = new HashMap<>();
    
    @Override
    public Department subcsribe(RemoteAtm atm, BoxSet boxes) {
        atms.put(atm, boxes);
        return this;
    }

    @Override
    public Department unsubcsribe(RemoteAtm atm) {
        atms.remove(atm);
        return this;
    }

    @Override
    public Map<RemoteAtm, BoxSet> replaceBoxes() {
        Map<RemoteAtm, BoxSet> result = new HashMap<>();
        forEachRemoteAtm( atm -> atm.replaceCurrencyBoxes(atms.get(atm)), result);
        return result;
    }

    @Override
    public Map<RemoteAtm, BigDecimal> getAmountsTotal() {
        Map<RemoteAtm, BigDecimal> result = new HashMap<>();
        forEachRemoteAtm( atm -> atm.getAmountTotal(), result);
        return result;
    }

    @Override
    public Map<RemoteAtm, BigDecimal> getAmountsToIssue() {
        Map<RemoteAtm, BigDecimal> result = new HashMap<>();
        forEachRemoteAtm( atm -> atm.getAmountToIssue(), result);
        return result;
    }
    
    private <T> void  forEachRemoteAtm(Function<RemoteAtm, T> fn, Map<RemoteAtm, T> accumulator) {
        for (RemoteAtm atm: atms.keySet()) {
            T value = fn.apply(atm);
            accumulator.put(atm, value);
        }
    }
}
