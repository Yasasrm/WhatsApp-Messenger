package com.wps.fwa.core.api.util.error;

import com.wps.fwa.core.api.util.ApiResponse;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@JsonIgnoreProperties
@Data
public class ServiceError implements ApiResponse {
    @JsonIgnore
    private String messageId;
    private RequestError requestError;
}
