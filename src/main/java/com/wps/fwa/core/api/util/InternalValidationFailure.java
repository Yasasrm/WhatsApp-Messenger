package com.wps.fwa.core.api.util;

import lombok.Data;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Data
public class InternalValidationFailure implements ApiResponse {
    private final String FAILURE_CODE = "INTERNAL_VALIDATION";
    private String description;
    private String messageId;
}
