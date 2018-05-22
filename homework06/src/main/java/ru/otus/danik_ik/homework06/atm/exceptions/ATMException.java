package ru.otus.danik_ik.homework06.atm.exceptions;

public class ATMException extends Exception {
    public ATMException(Throwable e) {
        super(e);
    }

    public ATMException() {
        super();
    }
}
