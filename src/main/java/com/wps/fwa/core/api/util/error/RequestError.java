package com.wps.fwa.core.api.util.error;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@JsonIgnoreProperties
@Data
public class RequestError {
    private ServiceException serviceException;
}
