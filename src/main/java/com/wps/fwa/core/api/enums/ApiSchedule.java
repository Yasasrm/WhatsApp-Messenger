package com.wps.fwa.core.api.enums;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public enum ApiSchedule {

    RE_TRY("R", "Re-Try Schedule", "RD"),
    RE_TRY_ERROR("RE", "Max Re-Try failed", null),
    ERROR("E", "ERROR", null);

    private String scheduleCode;
    private String scheduleDescription;
    private String scheduleDoneCode;

    ApiSchedule(String scheduleCode, String scheduleDescription, String scheduleDoneCode) {
        this.scheduleCode = scheduleCode;
        this.scheduleDescription = scheduleDescription;
        this.scheduleDoneCode = scheduleDoneCode;
    }

    public String getScheduleCode() {
        return scheduleCode;
    }

    public String getScheduleDescription() {
        return scheduleDescription;
    }

    public String getScheduleDoneCode() {
        return scheduleDoneCode;
    }
}
