package com.wps.fwa.core.api.exception;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public class TokenFailedException extends RuntimeException {
    public TokenFailedException() {
    }

    public TokenFailedException(String code) {
        super("Token retrieval request failed with response code: " + code);
    }

    public TokenFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenFailedException(Throwable cause) {
        super(cause);
    }
}
