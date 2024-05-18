package com.wps.fwa.core.api.controller;

import com.wps.fwa.core.api.enums.ApiConfig;
import com.wps.fwa.core.api.enums.ApiConstant;
import com.wps.fwa.core.api.service.LoggerService;
import com.wps.fwa.core.api.service.TrafficHandlerService;
import com.wps.fwa.core.api.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Controller
@RequestMapping(value = "delivery")
public class DeliveryController {

    @Autowired
    LoggerService logger;

    @Autowired
    TrafficHandlerService trafficHandlerService;

    @RequestMapping(value = "realtime/{messageId}", method = RequestMethod.GET)
    @ResponseBody
    public Response sendMessageFromMessageId(@PathVariable String messageId) {
        Response response;
        BigDecimal logId = logger.getNextLogId();
        try {
            logger.logRcord(logId, "/realtime/", messageId);
            response = trafficHandlerService.retrieverMessageAndSendFromMessageId(messageId);
            logger.logRcord(logId, "/realtime", response.toString());
        } catch (Exception e) {
            logger.logRcord(logId, "/realtime", e.getMessage());
            System.err.println("###nic-WhatsApp-API ERROR: ");
            e.printStackTrace();
            response = new Response();
            response.setCode(ApiConstant.ERROR.getCode());
            response.setStatus(ApiConstant.ERROR.getDescription());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "schedule/{batchId}", method = RequestMethod.GET)
    @ResponseBody
    public Response sendMessagesFromExecutionQueue(@PathVariable String batchId) {
        Response response;
        BigDecimal logId = logger.getNextLogId();
        try {
            logger.logRcord(logId, "/schedule/", batchId);
            response = trafficHandlerService.retrieverMessagesAndSendFromBatchId(batchId);
            logger.logRcord(logId, "/schedule", response.toString());
        } catch (Exception e) {
            logger.logRcord(logId, "/schedule", e.getMessage());
            System.err.println("###nic-WhatsApp-API ERROR: ");
            e.printStackTrace();
            response = new Response();
            response.setCode(ApiConstant.ERROR.getCode());
            response.setStatus(ApiConstant.ERROR.getDescription());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "queue", method = RequestMethod.GET)
    @ResponseBody
    public Response sendMessagesFromExecutionQueue() {
        Response response;
        BigDecimal logId = logger.getNextLogId();
        try {
            logger.logRcord(logId, "/queue", ApiConfig.BULK_QUEUE.getCode());
            response = trafficHandlerService.retrieverMessagesAndSendFromExecutionQueue();
            logger.logRcord(logId, "/queue", response.toString());
        } catch (Exception e) {
            logger.logRcord(logId, "/queue", e.getMessage());
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
