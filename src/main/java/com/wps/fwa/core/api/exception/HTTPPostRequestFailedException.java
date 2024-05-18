package com.wps.fwa.core.api.exception;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public class HTTPPostRequestFailedException extends RuntimeException {
    public HTTPPostRequestFailedException() {
    }

    public HTTPPostRequestFailedException(String code) {
        super("HTTP POST request failed with response code: " + code);
    }

    public HTTPPostRequestFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public HTTPPostRequestFailedException(Throwable cause) {
        super(cause);
    }
}
