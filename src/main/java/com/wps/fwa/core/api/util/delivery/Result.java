package com.wps.fwa.core.api.util.delivery;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@JsonIgnoreProperties
@Data
public class Result {

    private String bulkId;
    private Price price;
    private Status status;
    private Error error;
    private String messageId;
    private Date doneAt;
    private Integer messageCount;
    private Date sentAt;
    private String to;

}
