package com.wps.fwa.core.api.exception;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public class InvalidIdentifierException extends IllegalArgumentException {
    public InvalidIdentifierException() {
    }

    public InvalidIdentifierException(String s) {
        super(s);
    }

    public InvalidIdentifierException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIdentifierException(Throwable cause) {
        super(cause);
    }
}
