package ru.otus.danik_ik.homework07;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleDepartment implements Department {
    private Map<RemoteAtm, Supplier<BoxSet>> atms = new HashMap<>();
    
    @Override
    public Department register(RemoteAtm atm, Supplier<BoxSet> boxSetSupplier) {
        atms.put(atm, boxSetSupplier);
        return this;
    }

    @Override
    public Department unregister(RemoteAtm atm) {
        atms.remove(atm);
        return this;
    }

    @Override
    public Map<RemoteAtm, BoxSet> replaceBoxes() {
        Map<RemoteAtm, BoxSet> result = new HashMap<>();
        forEachRemoteAtm( atm -> atm.replaceCurrencyBoxes(atms.get(atm).get()), result);
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
