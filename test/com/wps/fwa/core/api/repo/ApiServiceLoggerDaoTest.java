package com.wps.fwa.core.api.repo;

import java.math.BigDecimal;

public class ApiServiceLoggerDaoTest implements ApiServiceLoggerDao {

    @Override
    public BigDecimal getNextLogId() {
        return null;
    }

    @Override
    public void setLoggerRecord(BigDecimal id, String method, String data) {
        System.out.println("Set Log record:");
        System.out.println("     id: "+id);
        System.out.println("     method: "+method);
        System.out.println("     data: "+data);
    }
}