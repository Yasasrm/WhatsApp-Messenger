package com.wps.fwa.core.api.exception;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public class MaximumTriesExceededException extends RuntimeException {
    public MaximumTriesExceededException() {
    }

    public MaximumTriesExceededException(String message) {
        super(message);
    }

    public MaximumTriesExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaximumTriesExceededException(Throwable cause) {
        super(cause);
    }
}
