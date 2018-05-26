package ru.otus.danik_ik.homework06.money;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum Denomination {
    ONE_HUNDRED (100),
    TWO_HUNDRED (200),
    FIVE_HUNDRED (500),
    ONE_THOUSAND (1000),
    TWO_THOUSAND (2000),
    FIVE_THOUSAND (5000),
    ;

    int denomination;

    Denomination(int denomination) {
        this.denomination = denomination;
    }

    public BigDecimal asBigDecimal() {
        return new BigDecimal(denomination);
    }

    public int asInt() {
        return denomination;
    }

    private static Map<Integer, Denomination> intMap = new HashMap<>();
    static {
        for (Denomination d : Denomination.values()) intMap.put(d.asInt(), d);
    }

    static Denomination of(int value) {
        Denomination result = intMap.get(value);
        if (result == null)
            throw new IllegalArgumentException( String.format("Недопустимый номинал: %d", value) );
        return result;
    }
}
