package com.wps.fwa.core.api.exception;

public class InvalidBulkIdException extends Exception {
    public InvalidBulkIdException() {
    }

    public InvalidBulkIdException(String message) {
        super(message);
    }

    public InvalidBulkIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBulkIdException(Throwable cause) {
        super(cause);
    }

    public InvalidBulkIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
