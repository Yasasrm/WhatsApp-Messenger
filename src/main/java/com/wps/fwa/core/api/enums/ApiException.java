package com.wps.fwa.core.api.enums;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public enum ApiException {

    UNKNOWN_NOTIFICATION("Unknown Notification Type"),
    UNKNOWN_ENVIRONMENT("Unknown Environment. Unable to find correct variables"),
    INVALID_NUMBER_OF_VARIABLES("Invalid number of variables for the selected template"),
    MAXIMUM_TRIES_EXCEEDED("Authentication failed and maximum tries exceeded"),
    INVALID_ID("Message Id not found"),
    ID_UNAVAILABLE("The system has no record of the Message ID in the received response."),
    INVALID_BATCH("Batch Id not found"),
    INVALID_RECEIVING_NUMBER("Receiver's number is not found in whitelist!"),
    INVALID_TEMPLATE("Invalid Template. Please define template using HZIC");


    private final String message;

    ApiException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
