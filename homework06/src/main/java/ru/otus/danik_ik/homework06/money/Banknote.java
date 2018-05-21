package ru.otus.danik_ik.homework06.money;

public class Banknote {
    private Denomination denomination;

    public Banknote(Denomination denomination) {
        this.denomination = denomination;
    }

    public Denomination getDenomination() {
        return denomination;
    }
}
