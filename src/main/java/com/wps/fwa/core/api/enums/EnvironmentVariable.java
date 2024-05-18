package com.wps.fwa.core.api.enums;
/**
 * @author YasasMa
 * @version 1.0.0.0
 */

import com.wps.fwa.core.api.exception.InvalidEnvironmentException;

public enum EnvironmentVariable {

    HUB_TOKEN_URL(
            "dev token url",
            "qa token url",
            "uat token url",
            "live token url"
    ),
    HUB_DELIVERY_URL(

            "dev_delivery_url",
            "qa_delivery_url",
            "uat_delivery_url",
            "live_delivery_url"
    ),
    HUB_CID(
            "DEV",
            "QA",
            "UAT",
            "LIVE"
    ),
    HUB_SK(
            "dev_sk",
            "qa_sk",
            "uat_sk",
            "live_sk"
    ),
    HUB_GRANT_TYPE(
            "client_credentials",
            "client_credentials",
            "client_credentials",
            "client_credentials"
    );

    private String dev;
    private String qa;
    private String uat;
    private String live;

    EnvironmentVariable(String dev, String qa, String uat, String live) {
        this.dev = dev;
        this.qa = qa;
        this.uat = uat;
        this.live = live;
    }

    public String get(String env) throws InvalidEnvironmentException {
        if (ApiConfig.DEV.getCode().equals(env))
            return dev;
        else if (ApiConfig.QA.getCode().equals(env))
            return qa;
        else if (ApiConfig.UAT.getCode().equals(env))
            return uat;
        else if (ApiConfig.LIVE.getCode().equals(env))
            return live;
        else
            throw new InvalidEnvironmentException(ApiException.UNKNOWN_ENVIRONMENT.getMessage(), new IllegalArgumentException());
    }
}
