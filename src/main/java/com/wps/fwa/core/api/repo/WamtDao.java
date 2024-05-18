package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.domain.Wamt;

import java.util.Optional;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public interface WamtDao {

    Optional<Wamt> findTemplate(String templateId);

}
