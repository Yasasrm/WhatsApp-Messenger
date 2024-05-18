package com.wps.fwa.core.api.repo;

import java.util.List;
import java.util.Optional;

public class WamcDaoNullStub implements WamcDao {

    @Override
    public Optional<List<String>> getExecutionQueueBatchIdList() {
        return Optional.empty();
    }
}