package com.wps.fwa.core.api.service;

import java.math.BigDecimal;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public interface LoggerService {

    BigDecimal getNextLogId();

    void logRcord(BigDecimal id, String method, String data);

}
