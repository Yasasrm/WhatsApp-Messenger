package com.wps.fwa.core.api.util.message;

import lombok.Data;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Data
public class Message {

    private String from;
    private String to;
    private String messageId;
    private Content content;

}
