package com.wps.fwa.core.api.service;

import com.wps.fwa.core.api.repo.ApiServiceLoggerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Service
public class LoggerServiceImpl implements LoggerService {

    @Autowired
    ApiServiceLoggerDao apiServiceLoggerDao;

    @Override
    public BigDecimal getNextLogId() {
        return apiServiceLoggerDao.getNextLogId();
    }

    @Override
    public void logRcord(BigDecimal id, String method, String data) {
        apiServiceLoggerDao.setLoggerRecord(id, method, data);
    }
}
