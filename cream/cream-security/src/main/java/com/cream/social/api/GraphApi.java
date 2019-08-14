/**
 * 
 */
package com.cream.social.api;

import org.springframework.util.MultiValueMap;

/**
 * @author v-chenxr04
 *
 */
public interface GraphApi {
    
    <T> T fetchObject(String connectionName, Class<T> type);

    <T> T fetchObject(String connectionName, Class<T> type, String... fields);

    <T> T fetchObject(String connectionName, Class<T> type, MultiValueMap<String, String> queryParameters);

    String getBaseGraphApiUrl();
}
