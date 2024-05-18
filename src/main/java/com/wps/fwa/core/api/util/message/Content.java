package com.wps.fwa.core.api.util.message;

import lombok.Data;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Data
public class Content {

    private String templateName;
    public TemplateData templateData;
    private String language;

}
