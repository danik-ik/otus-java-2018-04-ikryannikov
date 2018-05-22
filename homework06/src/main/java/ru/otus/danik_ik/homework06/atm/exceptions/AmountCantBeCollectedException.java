package ru.otus.danik_ik.homework06.atm.exceptions;

public class AmountCantBeCollectedException extends ATMException {
    public AmountCantBeCollectedException(Throwable e) {
        super(e);
    }

    public AmountCantBeCollectedException() {
        super();
    }
}
