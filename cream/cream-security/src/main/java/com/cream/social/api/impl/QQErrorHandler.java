/**
 * 
 */
package com.cream.social.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.cream.social.api.QQError;
import com.cream.social.api.QQErrors;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author v-chenxr04
 *
 */
class QQErrorHandler extends DefaultResponseErrorHandler {

    private final static Logger logger = LoggerFactory.getLogger(QQErrorHandler.class);
    private static final String QQ_PROVIDER_ID = "qq";
    
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        QQError error = extractErrorFromResponse(response);
        return super.hasError(response) || error != null;
    }
    
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        handleQQError(response.getStatusCode(), extractErrorFromResponse(response));
    }
    
    private void handleQQError(HttpStatus statusCode, QQError error) {
        if (error != null && error.getRet() != null) {
            int ret = error.getRet();
            
            if (ret == QQErrors.PARAM_EXPIRED_ACCESS_TOKEN) {
                throw new ExpiredAuthorizationException(QQ_PROVIDER_ID);
            } else {
                throw new UncategorizedApiException(QQ_PROVIDER_ID, "error ret: " + error.getRet() + ", error msg: " + error.getMsg(), null);
            }
        }
    }
    
    private QQError extractErrorFromResponse(ClientHttpResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        JsonNode jsonNode = mapper.readValue(readResponseBody(response), JsonNode.class);
        if (jsonNode.has("ret") && jsonNode.get("ret").intValue() != 0) {
            int ret = jsonNode.get("ret").intValue();
            String msg =  jsonNode.get("msg").asText();
            
            QQError error = new QQError(ret, msg);
            if (logger.isDebugEnabled()) {
                logger.debug("QQ error: ");
                logger.debug("   RET        : " + error.getRet());
                logger.debug("   MESSAGE        : " + error.getMsg());
            }
            return error;
        }
        return null;
    }
    
    private String readResponseBody(ClientHttpResponse response) throws IOException {
        String body = readFully(response.getBody());
        if (logger.isDebugEnabled()) {
            logger.debug("Error from QQ: " + body);
        }
        return body;
    }
    
    private String readFully(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            sb.append(reader.readLine());
        }
        return sb.toString();
    }
}
