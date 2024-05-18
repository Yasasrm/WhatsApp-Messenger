package com.wps.fwa.core.api.util.sent;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@JsonIgnoreProperties
@Data
public class SentMessage {

    private String to;
    private Integer messageCount;
    private String messageId;
    private SentStatus status;

}
