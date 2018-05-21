package ru.otus.danik_ik.homework06.money;

import java.math.BigDecimal;

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

    public BigDecimal asNum() {
        return new BigDecimal(denomination);
    }
}
