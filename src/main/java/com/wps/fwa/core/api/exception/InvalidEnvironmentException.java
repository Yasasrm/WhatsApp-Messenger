package com.wps.fwa.core.api.exception;

import java.io.IOException;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public class InvalidEnvironmentException extends IOException {
    public InvalidEnvironmentException() {
    }

    public InvalidEnvironmentException(String message) {
        super(message);
    }

    public InvalidEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEnvironmentException(Throwable cause) {
        super(cause);
    }
}
