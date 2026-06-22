package com.project.exception;

public class FineException extends RuntimeException {
    public FineException(String message) {
        super(message);
    }
    public FineException(String message, Throwable cause) {
        super(message, cause);
    }

}
