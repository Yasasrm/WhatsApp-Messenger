package com.wps.fwa.core.api.exception;

public class UnauthorizedReceiverException extends RuntimeException{
    public UnauthorizedReceiverException() {
    }

    public UnauthorizedReceiverException(String message) {
        super(message);
    }

    public UnauthorizedReceiverException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedReceiverException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedReceiverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
