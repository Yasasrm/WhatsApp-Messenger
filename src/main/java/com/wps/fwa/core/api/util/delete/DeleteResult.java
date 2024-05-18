package com.wps.fwa.core.api.util.delete;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@JsonIgnoreProperties
@Data
public class DeleteResult {

    private String messageId;
    private String from;
    private String to;
    private Date sentAt;
    private Date deletedAt;

}
