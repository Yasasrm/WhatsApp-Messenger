package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.enums.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Repository
public class WamcDaoImpl implements WamcDao {

    @Autowired
    EntityManager em;

    @Override
    public Optional<List<String>> getExecutionQueueBatchIdList() {
        Query query = em.createNativeQuery("SELECT wamc_value FROM wamc WHERE wamc_code = ?1");
        query.setParameter(1, ApiConfig.BULK_QUEUE.getCode());
        List<String> queue = null;
        try {
            queue = Arrays.asList(((String) query.getSingleResult()).split(","));
        } catch (Exception e) {
        }
        return Optional.ofNullable(queue);
    }
}
