package com.wps.fwa.core.api.exception;
/**
 * @author YasasMa
 * @version 1.0.0.0
 */

import java.io.IOException;

public class InvalidNotificationTypeException extends IOException {
    public InvalidNotificationTypeException() {
    }

    public InvalidNotificationTypeException(String message) {
        super(message);
    }

    public InvalidNotificationTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNotificationTypeException(Throwable cause) {
        super(cause);
    }
}
