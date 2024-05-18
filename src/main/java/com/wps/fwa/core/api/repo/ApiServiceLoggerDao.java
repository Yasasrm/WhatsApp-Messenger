package com.wps.fwa.core.api.repo;
/**
 * @author YasasMa
 * @version 1.0.0.0
 */

import java.math.BigDecimal;

public interface ApiServiceLoggerDao {

    BigDecimal getNextLogId();

    void setLoggerRecord(BigDecimal id, String method, String data);

}
