package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.domain.Wamt;

import java.util.Optional;

public class WamtDaoNullStub implements WamtDao {

    @Override
    public Optional<Wamt> findTemplate(String templateId) {
        return Optional.empty();
    }
}