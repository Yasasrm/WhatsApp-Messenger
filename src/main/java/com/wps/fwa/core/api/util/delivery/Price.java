package com.wps.fwa.core.api.util.delivery;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@JsonIgnoreProperties
@Data
public class Price {

    private Double pricePerMessage;
    private String currency;

}
