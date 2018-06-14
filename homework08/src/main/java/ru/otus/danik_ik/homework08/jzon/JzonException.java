package ru.otus.danik_ik.homework08.jzon;

public class JzonException extends RuntimeException {
    public JzonException() {
        super();
    }

    public JzonException(String message) {
        super(message);
    }

    public JzonException(String message, Throwable cause) {
        super(message, cause);
    }

    public JzonException(Throwable cause) {
        super(cause);
    }
}
