package com.wps.fwa.core.api.exception;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public class InvalidNumberOfVariables extends IllegalArgumentException {
    public InvalidNumberOfVariables() {
    }

    public InvalidNumberOfVariables(String s) {
        super(s);
    }

    public InvalidNumberOfVariables(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNumberOfVariables(Throwable cause) {
        super(cause);
    }
}
