package ru.otus.danik_ik.homework05.testo;

public class TestException extends Exception {
    public TestException(String message) {
        super(message);
    }

    public TestException(Throwable cause) {
        super(cause);
    }
}
