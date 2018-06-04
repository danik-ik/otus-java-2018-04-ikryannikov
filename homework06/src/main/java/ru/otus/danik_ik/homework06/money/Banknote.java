package ru.otus.danik_ik.homework06.money;

import java.util.Comparator;

public class Banknote {
    private Denomination denomination;

    public Banknote(Denomination denomination) {
        this.denomination = denomination;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public static Comparator<Banknote> getReversedDenominationComparator() {
        return (a, b) -> b.denomination.asInt() - a.denomination.asInt();
    }

}
