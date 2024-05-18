package com.wps.fwa.core.api.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Entity
@Table(name = "api_service_logger")
@Data
@NamedQuery(name = "asl.findRecord", query = "SELECT a FROM ApiServiceLogger a WHERE a.id = ?1")
public class ApiServiceLogger {

    @Id
    @Column(name = "ID")
    private BigDecimal id;

    @Column(name = "SERVICE_CODE")
    private String serviceCode;

    @Column(name = "SERVICE_METHOD")
    private String serviceMethod;

    @Setter(AccessLevel.NONE)
    @Column(name = "SERVICE_REQUEST", columnDefinition = "clob")
    private String serviceRequest;

    @Setter(AccessLevel.NONE)
    @Column(name = "SERVICE_RESPONSE", columnDefinition = "clob")
    private String serviceResponse;

    @Column(name = "LOGGER_C_USER")
    private String loggerCUser;

    @Column(name = "LOGGER_C_DATE")
    private Date loggerCDate;

    @Column(name = "LOGGER_M_USER")
    private String loggerMUser;

    @Column(name = "LOGGER_M_DATE")
    private Date loggerMDate;

    @Column(name = "VERSION")
    private Integer version;


    /**
     * Custom setter for service request without using lombok.
     * Oracle clob max size is 4000. This setter will save payload upto 4000 characters.
     * If payload exceed 4000 characters logging process will not produce an error.
     *
     * @param serviceRequest The Service Request.
     */
    public void setServiceRequest(String serviceRequest) {
        if (serviceRequest.length() > 4000) {
            this.serviceRequest = serviceRequest.substring(0, 4000);
        } else {
            this.serviceRequest = serviceRequest;
        }
    }

    /**
     * Custom setter for service response without using lombok.
     * Oracle clob max size is 4000. This setter will save payload upto 4000 characters.
     * If payload exceed 4000 characters logging process will not produce an error.
     *
     * @param serviceResponse The Service Response.
     */
    public void setServiceResponse(String serviceResponse) {
        if (serviceResponse.length() > 4000) {
            this.serviceResponse = serviceResponse.substring(0, 4000);
        } else {
            this.serviceResponse = serviceResponse;
        }
    }

}
