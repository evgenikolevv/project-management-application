package com.evgeni.exception;

public class WorkLogNotFoundException extends RuntimeException {

    public WorkLogNotFoundException(String message) {
        super(message);
    }

    public WorkLogNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
