package com.wps.fwa.core.api.util.sent;

import com.wps.fwa.core.api.util.ApiResponse;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@JsonIgnoreProperties
@Data
public class SentResponse implements ApiResponse {

    List<SentMessage> messages;
    String bulkId;

}
