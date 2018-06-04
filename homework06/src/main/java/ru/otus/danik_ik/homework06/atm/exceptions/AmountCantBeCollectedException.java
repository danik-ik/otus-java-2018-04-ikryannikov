package ru.otus.danik_ik.homework06.atm.exceptions;

public class AmountCantBeCollectedException extends ATMException {
    public AmountCantBeCollectedException(Throwable e) {
        super(e);
    }

    public AmountCantBeCollectedException(String message) {
        super(message);
    }

    public AmountCantBeCollectedException() {
        super();
    }

    public AmountCantBeCollectedException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AmountCantBeCollectedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
