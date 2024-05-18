package com.wps.fwa.core.api.enums;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public enum Template {

    OUTSTANDING_REMINDER("outstanding_reminder", 3),
    MONTHLY_PAYMENT("monthly_payment", 11);

    private String name;
    private Integer variableCount;

    Template(String name, Integer variableCount) {
        this.name = name;
        this.variableCount = variableCount;
    }

    public String getName() {
        return name;
    }

    public Integer getVariableCount() {
        return variableCount;
    }
}
