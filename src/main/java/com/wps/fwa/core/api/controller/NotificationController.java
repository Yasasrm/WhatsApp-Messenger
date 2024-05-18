package com.wps.fwa.core.api.controller;

import com.wps.fwa.core.api.enums.ApiConstant;
import com.wps.fwa.core.api.enums.ApiException;
import com.wps.fwa.core.api.exception.InvalidNotificationTypeException;
import com.wps.fwa.core.api.service.LoggerService;
import com.wps.fwa.core.api.service.TrafficHandlerService;
import com.wps.fwa.core.api.util.Response;
import com.wps.fwa.core.api.util.delete.DeleteReport;
import com.wps.fwa.core.api.util.delivery.DeliveryReport;
import com.wps.fwa.core.api.util.seen.SeenReport;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Controller
@RequestMapping(value = "notification")
public class NotificationController {

    @Autowired
    LoggerService logger;

    @Autowired
    TrafficHandlerService trafficHandlerService;

    /**
     * Takes all types of API responses. Vendor only support for one callback URL.
     * Delivery report types: delivery | seen | delete
     *
     * @param requestJson The delivery report.
     * @return After invoking the appropriate method in this class based on the delivery report type,
     * returns a standard 200 response received from that method.
     */
    @RequestMapping(value = "status_report", method = RequestMethod.POST)
    @ResponseBody
    public Response SetStatusReport(@RequestBody String requestJson) {
        Response response;
        ObjectMapper mapper = new ObjectMapper();
        try {
            try {
                DeliveryReport deliveryReport = mapper.readValue(requestJson, DeliveryReport.class);
                response = SetDeliveryReport(deliveryReport);
            } catch (IOException e) {
                try {
                    SeenReport seenReport = mapper.readValue(requestJson, SeenReport.class);
                    response = SetSeenReport(seenReport);
                } catch (IOException ioException) {
                    try {
                        DeleteReport deleteReport = mapper.readValue(requestJson, DeleteReport.class);
                        response = SetDeleteReport(deleteReport);
                    } catch (IOException exception) {
                        throw new InvalidNotificationTypeException(ApiException.UNKNOWN_NOTIFICATION.getMessage(), new ClassCastException());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("###nic-WhatsApp-API ERROR: ");
            e.printStackTrace();
            response = new Response();
            response.setCode(ApiConstant.ERROR.getCode());
            response.setStatus(ApiConstant.ERROR.getDescription());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "delivery", method = RequestMethod.POST)
    @ResponseBody
    public Response SetDeliveryReport(@RequestBody DeliveryReport deliveryReport) {
        BigDecimal logId = logger.getNextLogId();
        Response response;
        try {
            logger.logRcord(logId, "/delivery", deliveryReport.toString());
            response = trafficHandlerService.updateDeliveryReport(deliveryReport);
            logger.logRcord(logId, "/delivery", response.toString());
        } catch (Exception e) {
            logger.logRcord(logId, "/delivery", e.getMessage());
            System.err.println("###nic-WhatsApp-API ERROR: ");
            e.printStackTrace();
            response = new Response();
            response.setCode(ApiConstant.ERROR.getCode());
            response.setStatus(ApiConstant.ERROR.getDescription());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "seen", method = RequestMethod.POST)
    @ResponseBody
    public Response SetSeenReport(@RequestBody SeenReport seenReport) {
        BigDecimal logId = logger.getNextLogId();
        Response response;
        try {
            logger.logRcord(logId, "/seen", seenReport.toString());
            response = trafficHandlerService.updateDeliveryReport(seenReport);
            logger.logRcord(logId, "/seen", response.toString());
        } catch (Exception e) {
            logger.logRcord(logId, "/seen", e.getMessage());
            System.err.println("###nic-WhatsApp-API ERROR: ");
            e.printStackTrace();
            response = new Response();
            response.setCode(ApiConstant.ERROR.getCode());
            response.setStatus(ApiConstant.ERROR.getDescription());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Response SetDeleteReport(@RequestBody DeleteReport deleteReport) {
        BigDecimal logId = logger.getNextLogId();
        Response response;
        try {
            logger.logRcord(logId, "/delete", deleteReport.toString());
            response = trafficHandlerService.updateDeliveryReport(deleteReport);
            logger.logRcord(logId, "/delete", response.toString());
        } catch (Exception e) {
            logger.logRcord(logId, "/delete", e.getMessage());
            System.err.println("###nic-WhatsApp-API ERROR: ");
            e.printStackTrace();
            response = new Response();
            response.setCode(ApiConstant.ERROR.getCode());
            response.setStatus(ApiConstant.ERROR.getDescription());
            response.setMessage(e.getMessage());
        }
        return response;
    }


}
