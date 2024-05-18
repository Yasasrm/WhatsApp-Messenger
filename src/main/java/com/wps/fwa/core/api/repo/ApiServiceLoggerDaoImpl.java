package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.domain.ApiServiceLogger;
import com.wps.fwa.core.api.enums.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Repository
public class ApiServiceLoggerDaoImpl implements ApiServiceLoggerDao {

    @Autowired
    private EntityManager em;

    @Override
    @Transactional
    public BigDecimal getNextLogId() {
        Query query = em.createNativeQuery("SELECT seq_api_service_logger.NEXTVAL FROM dual");
        return (BigDecimal) query.getSingleResult();
    }

    @Override
    @Transactional
    public void setLoggerRecord(BigDecimal id, String method, String data) {
        ApiServiceLogger record;
        try {
            record = getApiServiceLogger(id);
        } catch (Exception e) {
            e.printStackTrace();
            record = null;
        }

        if (record == null) {
            record = new ApiServiceLogger();
            record.setId(id);
            record.setServiceCode(ApiConfig.API_CODE.getCode());
            record.setServiceMethod(method);
            record.setServiceRequest(data);
            record.setServiceResponse(ApiConfig.DEFAULT_RESPONSE.getCode());
            record.setLoggerCUser(ApiConfig.DEFAULT_USER.getCode());
            saveRecord(record);
        } else {
            record.setServiceResponse(ApiConfig.DEFAULT_RESPONSE.getCode().equals(record.getServiceResponse()) ? data : record.getServiceResponse() + " | " + data);
            record.setLoggerMUser(ApiConfig.DEFAULT_USER.getCode());
            updateRecord(record);
        }
    }

    private void saveRecord(ApiServiceLogger apiServiceLogger) {
        em.persist(apiServiceLogger);
    }

    private void updateRecord(ApiServiceLogger apiServiceLogger) {
        em.merge(apiServiceLogger);
    }

    private ApiServiceLogger getApiServiceLogger(BigDecimal id) {
        Query query = em.createNamedQuery("asl.findRecord");
        query.setParameter(1, id);
        return (ApiServiceLogger) query.getSingleResult();
    }

}
