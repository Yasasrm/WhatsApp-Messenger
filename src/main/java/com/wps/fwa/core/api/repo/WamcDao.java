package com.wps.fwa.core.api.repo;

import java.util.List;
import java.util.Optional;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public interface WamcDao {
    Optional<List<String>> getExecutionQueueBatchIdList();
}
