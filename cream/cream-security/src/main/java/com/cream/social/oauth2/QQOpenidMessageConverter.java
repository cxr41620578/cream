/**
 * 
 */
package com.cream.social.oauth2;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import com.cream.social.oauth2.QQOpenid;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author v-chenxr04
 *
 */
class QQOpenidMessageConverter extends AbstractGenericHttpMessageConverter<QQOpenid> {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    protected ObjectMapper objectMapper;

    public QQOpenidMessageConverter() {
        this(Jackson2ObjectMapperBuilder.json().build());
    }
    
    public QQOpenidMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        setDefaultCharset(DEFAULT_CHARSET);
        setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_HTML));
    }
    
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }
    
    @Override
    protected boolean canWrite(MediaType mediaType) {
        return false;
    }
    
    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return false;
    }
    
    @Override
    public QQOpenid read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        return readQQOpenid(inputMessage);
    }
    
    private QQOpenid readQQOpenid(HttpInputMessage inputMessage) throws IOException {
        Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
        String result = StreamUtils.copyToString(inputMessage.getBody(), charset);
        
        final String callbackStartStr = "callback(";
        int callbackStartIndex = result.indexOf(callbackStartStr);
        if (callbackStartIndex != -1) {
            int lastCallbackEndIndex = result.lastIndexOf(")");
            result = result.substring(callbackStartIndex + callbackStartStr.length(), lastCallbackEndIndex).trim();
            
            JsonNode jsonNode = this.objectMapper.readValue(result, JsonNode.class);
            String clientId = jsonNode.has("client_id") ? jsonNode.get("client_id").asText() : null;
            String openid = jsonNode.has("openid") ? jsonNode.get("openid").asText() : null;
            QQOpenid qqOpenid = new QQOpenid(clientId, openid);
            return qqOpenid;
        } else {
            throw new HttpMessageConversionException("format error");
        }
    }
    
    private Charset getContentTypeCharset(@Nullable MediaType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            return contentType.getCharset();
        }
        else if (contentType != null && contentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
            // Matching to AbstractJackson2HttpMessageConverter#DEFAULT_CHARSET
            return StandardCharsets.UTF_8;
        }
        else {
            Charset charset = getDefaultCharset();
            Assert.state(charset != null, "No default charset");
            return charset;
        }
    }

    @Override
    protected void writeInternal(QQOpenid t, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        throw new HttpMessageConversionException(null);
    }

    @Override
    protected QQOpenid readInternal(Class<? extends QQOpenid> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        return readQQOpenid(inputMessage);
    }
    
    @Override
    protected boolean supports(Class<?> clazz) {
        return QQOpenid.class.isAssignableFrom(clazz);
    }
}
