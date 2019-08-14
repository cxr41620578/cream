/**
 * 
 */
package com.cream.social.oauth2;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.social.support.FormMapHttpMessageConverter;

/**
 * @author v-chenxr04
 *
 */
class QQOAuth2MessageConverter extends FormMapHttpMessageConverter {

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        if (!Map.class.isAssignableFrom(clazz)) {
            return false;
        }
        if (mediaType == null) {
            return true;
        }
        if (MediaType.TEXT_HTML.equals(mediaType)) {
            return true;
        }
        for (MediaType supportedMediaType : getSupportedMediaTypes()) {
            // we can't read multipart
            if (!supportedMediaType.equals(MediaType.MULTIPART_FORM_DATA) &&
                supportedMediaType.includes(mediaType)) {
                return true;
            }
        }
        return false;
    }
}
