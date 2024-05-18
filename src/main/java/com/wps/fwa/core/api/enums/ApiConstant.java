package com.wps.fwa.core.api.enums;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public enum ApiConstant {

    SUCCESS("000", "S", "SUCCESS"),
    ERROR("999", "E", "ERROR");

    private final String id;
    private final String code;
    private final String description;

    ApiConstant(String id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
