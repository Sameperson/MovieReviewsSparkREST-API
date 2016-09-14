package com.sameperson.movies.exc;

public class DaoException extends Exception {

    private final Exception exception;

    public DaoException(Exception exception, String message) {
        super(message);
        this.exception = exception;

    }
}
