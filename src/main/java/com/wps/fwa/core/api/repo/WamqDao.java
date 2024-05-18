package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.domain.Wamq;

import java.util.List;
import java.util.Optional;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public interface WamqDao {

    Optional<Wamq> findMessageInfoFromQueue(String messageId);

    Optional<List<String>> getMessageIdListFromBatchId(String batchId);

    void deleteMessageInfoFromQueue(String messageId);

    void deleteBulk(String batchId);

}
