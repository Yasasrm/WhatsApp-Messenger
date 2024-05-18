package com.wps.fwa.core.api.util.delivery;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@JsonIgnoreProperties
@Data
public class Error {

    private Integer id;
    private String name;
    private String description;
    private Integer groupId;
    private String groupName;
    private Boolean permanent;

}
