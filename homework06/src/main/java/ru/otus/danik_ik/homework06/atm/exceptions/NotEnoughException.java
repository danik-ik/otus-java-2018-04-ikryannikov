package ru.otus.danik_ik.homework06.atm.exceptions;

public class NotEnoughException extends ATMException {
    public NotEnoughException() {
        super();
    }

    public NotEnoughException(String message) {
        super(message);
    }

    public NotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
