package com.wps.fwa.core.api.enums;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public enum ApiConfig {

    API_CODE("FWA"),
    DEV("DEV"),
    QA("QA"),
    UAT("UAT"),
    LIVE("LIVE"),
    DEFAULT_RESPONSE("Error Occurred before process response."),
    DELIVERY_CONFIRM("Delivery report received."),
    DELIVERY_CONFIRM_NO_MESSAGE("No message found for deliver."),
    DEFAULT_USER("FWA-API"),
    LANGUAGE("ID"),
    SENDER("sender-number"),
    BULK_QUEUE("FWA_QUE"),
    RETRY_LIMIT("4"),
    COUNTRY_CODE("62");

    private String code;

    ApiConfig(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
