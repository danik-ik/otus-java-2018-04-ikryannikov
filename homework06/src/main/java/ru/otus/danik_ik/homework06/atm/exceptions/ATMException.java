package ru.otus.danik_ik.homework06.atm.exceptions;

public class ATMException extends Exception {
    public ATMException() {
        super();
    }

    public ATMException(String message) {
        super(message);
    }

    public ATMException(String message, Throwable cause) {
        super(message, cause);
    }

    public ATMException(Throwable cause) {
        super(cause);
    }

    protected ATMException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
