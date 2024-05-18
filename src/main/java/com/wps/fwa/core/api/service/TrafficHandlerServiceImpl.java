package com.wps.fwa.core.api.service;

import com.wps.fwa.core.api.config.nicEnvReader;
import com.wps.fwa.core.api.domain.Wamh;
import com.wps.fwa.core.api.domain.Wamq;
import com.wps.fwa.core.api.domain.Wamt;
import com.wps.fwa.core.api.enums.*;
import com.wps.fwa.core.api.exception.*;
import com.wps.fwa.core.api.repo.WamcDao;
import com.wps.fwa.core.api.repo.WamhDao;
import com.wps.fwa.core.api.repo.WamqDao;
import com.wps.fwa.core.api.repo.WamtDao;
import com.wps.fwa.core.api.util.ApiResponse;
import com.wps.fwa.core.api.util.InternalValidationFailure;
import com.wps.fwa.core.api.util.OAuth2;
import com.wps.fwa.core.api.util.Response;
import com.wps.fwa.core.api.util.delete.DeleteReport;
import com.wps.fwa.core.api.util.delete.DeleteResult;
import com.wps.fwa.core.api.util.delivery.DeliveryReport;
import com.wps.fwa.core.api.util.delivery.Result;
import com.wps.fwa.core.api.util.error.ServiceError;
import com.wps.fwa.core.api.util.message.*;
import com.wps.fwa.core.api.util.seen.SeenReport;
import com.wps.fwa.core.api.util.seen.SeenResult;
import com.wps.fwa.core.api.util.sent.SentMessage;
import com.wps.fwa.core.api.util.sent.SentResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Service
public class TrafficHandlerServiceImpl implements TrafficHandlerService {

    @Autowired
    WamqDao wamqDao;

    @Autowired
    WamhDao wamhDao;

    @Autowired
    WamtDao wamtDao;

    @Autowired
    WamcDao wamcDao;

    private static OAuth2 oAuth2 = new OAuth2();

    private int messageSendingTry = 0;

    private static void getAccessToken() throws Exception {

        String env = nicEnvReader.getProperty("nic.env");
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

        String url = EnvironmentVariable.HUB_TOKEN_URL.get(env);
        String clientId = EnvironmentVariable.HUB_CID.get(env);
        String clientSecret = EnvironmentVariable.HUB_SK.get(env);
        String grantType = EnvironmentVariable.HUB_GRANT_TYPE.get(env);
        String credentials = clientId + ":" + clientSecret;
        String authorization = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", authorization);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);
        String postData = "client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=" + grantType;
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(postData);
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String responseBody = response.toString();
            ObjectMapper mapper = new ObjectMapper();
            oAuth2 = mapper.readValue(responseBody, OAuth2.class);
            connection.disconnect();
        } else {
            connection.disconnect();
            throw new TokenFailedException("" + responseCode);
        }

    }

    private Response sendMessageAndUpdateDeliveryReport(TemplateMessage templateMessage) throws Exception {

        messageSendingTry++;
        SentResponse sentResponse;
        Response response;
        String env = nicEnvReader.getProperty("nic.env");
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        String apiUrl = EnvironmentVariable.HUB_DELIVERY_URL.get(env);

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + oAuth2.getAccessToken());
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(messageAsString(templateMessage));
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            sentResponse = decodeResponse(connection.getInputStream(), new SentResponse());
            response = updateDeliveryReport(sentResponse);
            connection.disconnect();
        } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            if (messageSendingTry < Integer.parseInt(ApiConfig.RETRY_LIMIT.getCode())) {
                getAccessToken();
                response = sendMessageAndUpdateDeliveryReport(templateMessage);
            } else {
                ServiceError serviceError = decodeResponse(connection.getErrorStream(), new ServiceError());
                connection.disconnect();
                for (Message msg : templateMessage.getMessages()) {
                    serviceError.setMessageId(msg.getMessageId());
                    updateDeliveryReport(serviceError);
                }
                messageSendingTry = 0;
                throw new MaximumTriesExceededException(ApiException.MAXIMUM_TRIES_EXCEEDED.getMessage());
            }
        } else {
            ServiceError serviceError = decodeResponse(connection.getErrorStream(), new ServiceError());
            connection.disconnect();
            for (Message msg : templateMessage.getMessages()) {
                serviceError.setMessageId(msg.getMessageId());
                updateDeliveryReport(serviceError);
            }
            messageSendingTry = 0;
            throw new HTTPPostRequestFailedException("" + responseCode);
        }
        messageSendingTry = 0;
        return response;
    }

    private String messageAsString(TemplateMessage templateMessage) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(templateMessage);
    }

    /**
     * Decode the API response and generate the necessary object.
     *
     * @param inputStream  The API response body.
     * @param responseType Empty Object of required return type.
     * @return Required object that is created from inputStream.
     */
    private <T> T decodeResponse(InputStream inputStream, T responseType) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String responseBody = response.toString();
        ObjectMapper mapper = new ObjectMapper();
        return (T) mapper.readValue(responseBody, responseType.getClass());
    }

    /**
     * Retrieve message from WAMQ.
     *
     * @param messageId The message Id (WAMQ primary key).
     * @return Message object.
     */
    private Message validateAndGenerateMessage(String messageId) throws Exception {
        Wamq wamq = wamqDao.findMessageInfoFromQueue(messageId).orElse(new Wamq());
        validMessageInfo(wamq);
        validMessageTemplate(wamq);
        return generateMessage(wamq);
    }

    private Message generateMessage(Wamq wamq) {

        List<String> placeholders = wamq.getWamqParaList() != null ? Arrays.asList(wamq.getWamqParaList().split("\\|")) : new ArrayList<>();

        Body body = new Body();
        body.setPlaceholders(placeholders);

        TemplateData templateData = new TemplateData();
        templateData.setBody(body);

        Content content = new Content();
        content.setTemplateName(wamq.getWamqTemplate());
        content.setTemplateData(templateData);
        content.setLanguage(ApiConfig.LANGUAGE.getCode());

        Message message = new Message();
        message.setFrom(ApiConfig.SENDER.getCode());
        message.setTo(checkAndGetReceiversNumberWithCountryCode(wamq.getWamqTo()));
        message.setMessageId(wamq.getWamqMsgId());
        message.setContent(content);
        return message;
    }

    private String checkAndGetReceiversNumberWithCountryCode(String wamqTo) {
        return wamqTo.startsWith(ApiConfig.COUNTRY_CODE.getCode()) ? wamqTo : wamqTo.replaceFirst("0", ApiConfig.COUNTRY_CODE.getCode());
    }

    private void validMessageTemplate(Wamq wamq) throws Exception {
        Wamt wamt = wamtDao.findTemplate(wamq.getWamqTemplate()).orElse(new Wamt());
        if (wamt.getWamtTemplate() == null)
            updateDeliveryReportAndThrowError(wamq.getWamqMsgId(), new InvalidIdentifierException(ApiException.INVALID_TEMPLATE.getMessage(), new RuntimeException()));

        List<String> placeholders = wamq.getWamqParaList() != null ? Arrays.asList(wamq.getWamqParaList().split("\\|")) : new ArrayList<>();

        if (placeholders.size() != wamt.getWamtParaCount())
            updateDeliveryReportAndThrowError(wamq.getWamqMsgId(), new InvalidNumberOfVariables(ApiException.INVALID_NUMBER_OF_VARIABLES.getMessage(), new RuntimeException()));

    }

    private void validMessageInfo(Wamq wamq) throws Exception {

        if (wamq.getWamqMsgId() == null)
            throw new InvalidIdentifierException(ApiException.INVALID_ID.getMessage(), new RuntimeException());

        if(!isWhiteListedNumber(checkAndGetReceiversNumberWithCountryCode(wamq.getWamqTo())))
            updateDeliveryReportAndThrowError(wamq.getWamqMsgId(), new UnauthorizedReceiverException(ApiException.INVALID_RECEIVING_NUMBER.getMessage(), new RuntimeException()));



    }

    private void updateDeliveryReportAndThrowError(String messageId, Exception e) throws Exception {
        InternalValidationFailure validationFailure = new InternalValidationFailure();
        validationFailure.setDescription(e.getMessage());
        validationFailure.setMessageId(messageId);
        updateDeliveryReport(validationFailure);
        throw e;
    }

    /**
     * This method includes a safety check to prevent the sending of WhatsApp messages to customers in non-production environments.
     *
     * @param receivingNumber The Receiver's phone number.
     */
    public boolean isWhiteListedNumber(String receivingNumber) throws IOException {
        if (isProduction()) {
            return true;
        } else {
            return WhiteList.isReceiverExist(receivingNumber);
        }
    }

    private boolean isProduction() throws IOException {
        String env = nicEnvReader.getProperty("nic.env");
        if (ApiConfig.LIVE.getCode().equals(env))
            return true;
        else
            return false;
    }

    private TemplateMessage generateTemplateMessageFromMessageId(String messageId) throws Exception {
        Message message = validateAndGenerateMessage(messageId);
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setMessages(messages);
        return templateMessage;
    }

    private TemplateMessage generateTemplateMessageFromBatchId(String batchId) throws InvalidBulkIdException {
        List<String> messageIds = wamqDao.getMessageIdListFromBatchId(batchId).orElse(new ArrayList<>());
        if (messageIds.isEmpty())
            throw new InvalidBulkIdException(ApiException.INVALID_BATCH.getMessage(), new IllegalArgumentException());
        List<Message> messages = new ArrayList<>();
        Message message;
        for (String messageId : messageIds) {
            try {
                message = validateAndGenerateMessage(messageId);
            } catch (Exception e) {
                message = null;
            }
            if (message != null)
                messages.add(message);
        }
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setMessages(messages);
        return templateMessage;
    }

    @Override
    public Response retrieverMessageAndSendFromMessageId(String messageId) throws Exception {
        TemplateMessage templateMessage = generateTemplateMessageFromMessageId(messageId);
        return sendMessageAndUpdateDeliveryReport(templateMessage);
    }

    @Override
    public Response retrieverMessagesAndSendFromBatchId(String batchId) throws Exception {
        TemplateMessage templateMessage = generateTemplateMessageFromBatchId(batchId);
        return templateMessage.getMessages().isEmpty()? getResponseWhenNoMessagesFound() : sendMessageAndUpdateDeliveryReport(templateMessage);
    }

    private Response getResponseWhenNoMessagesFound() {
        Response response = new Response();
        response.setCode(ApiConstant.SUCCESS.getId());
        response.setStatus(ApiConstant.SUCCESS.getDescription());
        response.setMessage(ApiConfig.DELIVERY_CONFIRM.getCode());
        return response;
    }

    @Override
    public Response retrieverMessagesAndSendFromExecutionQueue() {
        String message = "";
        List<String> batchIdList = wamcDao.getExecutionQueueBatchIdList().orElse(new ArrayList<>());
        for (String batchId : batchIdList) {
            try {
                Response rsp = retrieverMessagesAndSendFromBatchId(batchId);
                message += "|BatchId[" + batchId + "] : " + rsp.getMessage();
            } catch (Exception e) {
                System.err.println("###nic-WhatsApp-API ERROR: ");
                e.printStackTrace();
                message += "|BatchId[" + batchId + "] : " + e.getMessage();
            }
        }
        Response response = new Response();
        response.setCode(ApiConstant.SUCCESS.getId());
        response.setStatus(ApiConstant.SUCCESS.getDescription());
        response.setMessage(message);
        return response;
    }

    /**
     * Takes API response and log necessary details to WAMH.
     *
     * @param deliveryReport The delivery report. This type should be a implementation of ApiResponse.
     * @return General 200 response.
     */
    @Override
    public <T extends ApiResponse> Response updateDeliveryReport(T deliveryReport) {
        if (deliveryReport instanceof InternalValidationFailure) {
            processInternalValidationFailureDeliveryReport((InternalValidationFailure) deliveryReport);
        } else if (deliveryReport instanceof ServiceError) {
            processServiceErrorDeliveryReport((ServiceError) deliveryReport);
        } else if (deliveryReport instanceof SentResponse) {
            processSentResponseDeliveryReport((SentResponse) deliveryReport);
        } else if (deliveryReport instanceof DeliveryReport) {
            processDeliveryReport((DeliveryReport) deliveryReport);
        } else if (deliveryReport instanceof SeenReport) {
            processSeenReportDeliveryReport((SeenReport) deliveryReport);
        } else if (deliveryReport instanceof DeleteReport) {
            processDeleteReportDeliveryReport((DeleteReport) deliveryReport);
        }

        Response response = new Response();
        response.setCode(ApiConstant.SUCCESS.getId());
        response.setStatus(ApiConstant.SUCCESS.getDescription());
        response.setMessage(ApiConfig.DELIVERY_CONFIRM.getCode());
        return response;
    }

    private void processDeleteReportDeliveryReport(DeleteReport deliveryReport) {
        List<DeleteResult> results = deliveryReport.getResults();
        for (DeleteResult result : results) {
            Wamh wamh = wamhDao.findMessageInfoFromHistory(result.getMessageId()).orElse(new Wamh());
            if (wamh.getWamhMsgId() == null)
                throw new InvalidIdentifierException(ApiException.ID_UNAVAILABLE.getMessage());

            wamh.setWamhDeleteAt(result.getDeletedAt());
            wamhDao.setDeliveryStatus(wamh);
        }
    }

    private void processSeenReportDeliveryReport(SeenReport deliveryReport) {
        List<SeenResult> results = deliveryReport.getResults();
        for (SeenResult result : results) {
            Wamh wamh = wamhDao.findMessageInfoFromHistory(result.getMessageId()).orElse(new Wamh());
            if (wamh.getWamhMsgId() == null)
                throw new InvalidIdentifierException(ApiException.ID_UNAVAILABLE.getMessage());

            wamh.setWamhSeenAt(result.getSeenAt());
            wamhDao.setDeliveryStatus(wamh);
        }
    }

    private void processDeliveryReport(DeliveryReport deliveryReport) {
        List<Result> results = deliveryReport.getResults();
        for (Result result : results) {
            Wamh wamh = wamhDao.findMessageInfoFromHistory(result.getMessageId()).orElse(new Wamh());
            if (wamh.getWamhMsgId() == null)
                throw new InvalidIdentifierException(ApiException.ID_UNAVAILABLE.getMessage());

            wamh.setWamhBulkId(result.getBulkId());
            wamh.setWamhCostPrMsg(result.getPrice().getPricePerMessage());
            wamh.setWamhCurrCode(result.getPrice().getCurrency());
            wamh.setWamhDeliverMsg(result.getStatus().getDescription());
            wamh.setWamhDeliverTime(new Date());
            wamh.setWamhErrorCode(result.getError().getName());
            wamh.setWamhError(result.getError().getDescription());
            wamh.setWamhDoneAt(result.getDoneAt());
            wamh.setWamhSentAt(result.getSentAt());
            wamhDao.setDeliveryStatus(wamh);
        }
    }

    private void processSentResponseDeliveryReport(SentResponse sentResponse) {
        List<SentMessage> messages = sentResponse.getMessages();
        for (SentMessage message : messages) {
            Wamq wamq = wamqDao.findMessageInfoFromQueue(message.getMessageId()).orElse(new Wamq());
            if (wamq.getWamqMsgId() == null)
                throw new InvalidIdentifierException(ApiException.ID_UNAVAILABLE.getMessage(), new RuntimeException());

            Wamh wamh = getHistoryRecordFromQueueInfo(wamq);
            wamh.setWamhBulkId(sentResponse.getBulkId());
            wamh.setWamhPendingMsg(message.getStatus().getDescription());
            wamh.setWamhPendingTime(new Date());
            wamh.setWamhMsgCount(message.getMessageCount());
            wamh.setWamhScheduleId(null);
            wamhDao.setDeliveryStatus(wamh);
            wamqDao.deleteMessageInfoFromQueue(wamq.getWamqMsgId());
        }
    }

    private void processServiceErrorDeliveryReport(ServiceError serviceError) {
        Wamq wamq = wamqDao.findMessageInfoFromQueue(serviceError.getMessageId()).orElse(new Wamq());
        if (wamq.getWamqMsgId() != null) {
            Wamh wamh = getHistoryRecordFromQueueInfo(wamq);
            wamh.setWamhErrorCode(serviceError.getRequestError().getServiceException().getMessageId());
            wamh.setWamhError(serviceError.getRequestError().getServiceException().getText());
            wamh.setWamhScheduleId(getScheduleCodeFromMessageId(wamq.getWamqMsgId()));
            wamhDao.setDeliveryStatus(wamh);
            wamqDao.deleteMessageInfoFromQueue(wamq.getWamqMsgId());
        }
    }

    private String getScheduleCodeFromMessageId(String wamqMsgId) {
        if(reTryCountExceedMaximumReTryTimes(wamqMsgId)){
            return ApiSchedule.RE_TRY_ERROR.getScheduleCode();
        } else {
            return ApiSchedule.RE_TRY.getScheduleCode();
        }
    }

    private boolean reTryCountExceedMaximumReTryTimes(String wamqMsgId) {
        Integer messageReTryCount = wamhDao.getMessageReTryCount(wamqMsgId);
        return !(messageReTryCount < Integer.parseInt(ApiConfig.RETRY_LIMIT.getCode()));
    }

    private void processInternalValidationFailureDeliveryReport(InternalValidationFailure validationFailure) {
        Wamq wamq = wamqDao.findMessageInfoFromQueue(validationFailure.getMessageId()).orElse(new Wamq());
        Wamh wamh = getHistoryRecordFromQueueInfo(wamq);
        wamh.setWamhErrorCode(validationFailure.getFAILURE_CODE());
        wamh.setWamhError(validationFailure.getDescription());
        wamh.setWamhScheduleId(ApiSchedule.ERROR.getScheduleCode());
        wamhDao.setDeliveryStatus(wamh);
        wamqDao.deleteMessageInfoFromQueue(wamq.getWamqMsgId());
    }

    private Wamh getHistoryRecordFromQueueInfo(Wamq wamq) {
        Wamh wamh = wamhDao.findMessageInfoFromHistory(wamq.getWamqMsgId()).orElse(new Wamh());
        wamh.setWamhId(null);
        wamh.setWamhMsgId(wamq.getWamqMsgId());
        wamh.setWamhTo(wamq.getWamqTo());
        wamh.setWamhTemplate(wamq.getWamqTemplate());
        wamh.setWamhParaList(wamq.getWamqParaList());
        wamh.setWamhCDate(wamq.getWamqCDate());
        wamh.setWamhCUser(wamq.getWamqCUser());
        wamh.setWamhBatchId(wamq.getWamqBatchId());
        return wamh;
    }

}