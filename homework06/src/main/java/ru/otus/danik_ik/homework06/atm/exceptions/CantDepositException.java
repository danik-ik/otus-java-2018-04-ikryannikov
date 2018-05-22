package ru.otus.danik_ik.homework06.atm.exceptions;

public class CantDepositException extends ATMException {
    public CantDepositException() {
        super();
    }

    public CantDepositException(String message) {
        super(message);
    }

    public CantDepositException(String message, Throwable cause) {
        super(message, cause);
    }

    public CantDepositException(Throwable cause) {
        super(cause);
    }

    protected CantDepositException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
