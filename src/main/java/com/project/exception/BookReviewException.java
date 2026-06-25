package com.project.exception;

public class BookReviewException extends RuntimeException {
    public BookReviewException(String message) {
        super(message);
    }
    public BookReviewException(String message, Throwable cause) {
        super(message, cause);
    }

}
