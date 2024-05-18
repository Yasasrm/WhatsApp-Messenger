package com.wps.fwa.core.api.service;

import com.wps.fwa.core.api.enums.ApiConfig;
import com.wps.fwa.core.api.enums.ApiConstant;
import com.wps.fwa.core.api.repo.*;
import com.wps.fwa.core.api.util.Response;
import com.wps.fwa.core.api.util.error.RequestError;
import com.wps.fwa.core.api.util.error.ServiceError;
import com.wps.fwa.core.api.util.error.ServiceException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;

public class TrafficHandlerServiceTest extends TrafficHandlerServiceImpl {
    private TrafficHandlerService trafficHandlerService;

    private int messageSendingTry = 2147483640;

    @Before
    public void setupService(){
        trafficHandlerService = new TrafficHandlerServiceImpl();
        wamqDao = new WamqDaoNotNullStub();
        wamhDao = new WamhDaoNotNullStub();
        wamtDao = new WamtDaoNullStub();
        wamcDao = new WamcDaoNullStub();
    }

    @Test
    public void testUpdateNullDeliveryReport(){
        Response response = new Response();
        response.setCode(ApiConstant.SUCCESS.getId());
        response.setStatus(ApiConstant.SUCCESS.getDescription());
        response.setMessage(ApiConfig.DELIVERY_CONFIRM.getCode());
        assertEquals(response, trafficHandlerService.updateDeliveryReport(null));
    }

    @Test
    public void testUpdateServiceErrorReport(){
        setNotNullObjects();
        ServiceError serviceError = getUnAuthorizedServiceError();
        Response response = new Response();
        response.setCode(ApiConstant.SUCCESS.getId());
        response.setStatus(ApiConstant.SUCCESS.getDescription());
        response.setMessage(ApiConfig.DELIVERY_CONFIRM.getCode());
        assertEquals(response, trafficHandlerService.updateDeliveryReport(serviceError));
    }

    @Test
    public void testSendMessage(){
        for (int i = 0; i < 10; i++)
            sendMessageAndUpdateDeliveryReport(500);
    }

    @Test
    public void integerShouldReturnInteger(){
        Integer i = 35;
        assertEquals(i, castToInteger(i));
    }

    @Test
    public void bigDecimalShouldReturnInteger(){
        Integer i = 35;
        BigDecimal b = new BigDecimal(35);
        assertEquals(i, castToInteger(b));
    }

    @Test
    public void longShouldReturnInteger(){
        Integer i = 35;
        Long l = 35l;
        assertEquals(i, castToInteger(l));
    }

    @Test
    public void unexpectedShouldReturnZero(){
        Integer i = 0;
        assertEquals(i, castToInteger(null));
    }

    private Integer castToInteger(Object result) {
        if (result instanceof Integer)
            return (Integer) result;
        if (result instanceof BigDecimal)
            return ((BigDecimal) result).intValueExact();
        if (result instanceof Long)
            return ((Long) result).intValue();
        return 0;
    }

    private void sendMessageAndUpdateDeliveryReport(int code) {

        messageSendingTry++;
        System.out.println("Send message");

        int responseCode = code;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("200 success");
        } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            if (messageSendingTry < Integer.parseInt(ApiConfig.RETRY_LIMIT.getCode())) {
                System.out.println("retry");
                sendMessageAndUpdateDeliveryReport(code);
            } else {
                System.out.println("retry limit exceed");
                messageSendingTry = 0;
            }
        } else {
            System.out.println("Other Error");
        }
        System.out.println("End");
        System.out.println("messageSendingTry: "+messageSendingTry);
    }

    private void setNotNullObjects() {
        wamqDao = new WamqDaoNotNullStub();
        wamhDao = new WamhDaoNotNullStub();
    }

    private ServiceError getUnAuthorizedServiceError() {
        ServiceException serviceException = new ServiceException();
        serviceException.setMessageId("1");
        serviceException.setText("Error");
        RequestError requestError = new RequestError();
        requestError.setServiceException(serviceException);
        ServiceError serviceError = new ServiceError();
        serviceError.setMessageId("1");
        serviceError.setRequestError(requestError);
        return serviceError;
    }
}