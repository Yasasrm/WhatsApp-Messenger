package com.wps.fwa.core.api.util.error;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@JsonIgnoreProperties
@Data
public class ServiceException {
    private String messageId;
    private String text;
}
