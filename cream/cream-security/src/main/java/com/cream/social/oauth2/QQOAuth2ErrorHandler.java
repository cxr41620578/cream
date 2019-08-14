/**
 * 
 */
package com.cream.social.oauth2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author v-chenxr04
 *
 */
public class QQOAuth2ErrorHandler extends DefaultResponseErrorHandler {
    
    private final static Logger logger = LoggerFactory.getLogger(QQOAuth2ErrorHandler.class);
    private static final String QQ_PROVIDER_ID = "qq";
    
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        QQOAuth2Error error = extractErrorFromResponse(response);
        return super.hasError(response) || (error != null && !Integer.valueOf(0).equals(error.getError()));
    }
    
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        handleQQOAuth2Error(response.getStatusCode(), extractErrorFromResponse(response));
    }
    
    private void handleQQOAuth2Error(HttpStatus statusCode, QQOAuth2Error error) {
        if (error != null && error.getError() != null) {
            int code = error.getError();
            
            if (code == QQOAuth2Errors.PARAM_APPID) {
                throw new InvalidAuthorizationException(QQ_PROVIDER_ID, error.getErrorDescription());
            } else {
                throw new UncategorizedApiException(QQ_PROVIDER_ID, "error code: " + error.getError() + ", error description: " + error.getErrorDescription(), null);
            }
        }
    }
    
    private QQOAuth2Error extractErrorFromResponse(ClientHttpResponse response) throws IOException {
        String body = readResponseBody(response);
        final String callbackStartStr = "callback(";
        int callbackStartIndex = body.indexOf(callbackStartStr);
        if (callbackStartIndex != -1) {
            int lastCallbackEndIndex = body.lastIndexOf(")");
            body = body.substring(callbackStartIndex + callbackStartStr.length(), lastCallbackEndIndex).trim();
            
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            JsonNode jsonNode = mapper.readValue(body, JsonNode.class);
            if (jsonNode.has("error")) {
                int error = jsonNode.get("error").intValue();
                String errorDescription =  jsonNode.get("error_description").asText();
                
                QQOAuth2Error qqOAuth2Error = new QQOAuth2Error(error, errorDescription);
                if (logger.isDebugEnabled()) {
                    logger.debug("QQ error: ");
                    logger.debug("   CODE        : " + qqOAuth2Error.getError());
                    logger.debug("   DESCRIPTION        : " + qqOAuth2Error.getErrorDescription());
                }
                return qqOAuth2Error;
            }
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
