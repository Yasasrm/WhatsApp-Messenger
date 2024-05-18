package com.wps.fwa.core.api.service;

import com.wps.fwa.core.api.util.ApiResponse;
import com.wps.fwa.core.api.util.Response;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public interface TrafficHandlerService {

    Response retrieverMessageAndSendFromMessageId(String messageId) throws Exception;

    Response retrieverMessagesAndSendFromBatchId(String batchId) throws Exception;

    Response retrieverMessagesAndSendFromExecutionQueue();

    <T extends ApiResponse> Response updateDeliveryReport(T deliveryReport);
}
