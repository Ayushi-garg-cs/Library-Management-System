package com.project.exception;

public class BookLoanException extends RuntimeException {
    public BookLoanException(String message) {
        super(message);
    }
    public BookLoanException(String message, Throwable cause) {
        super(message, cause);
    }
}
