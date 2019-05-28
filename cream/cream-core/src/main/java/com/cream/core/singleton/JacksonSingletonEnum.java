/**
 * 
 */
package com.cream.core.singleton;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author cream
 *
 */
public enum JacksonSingletonEnum {

    OBJECT_MAPPER;
    
    private ObjectMapper objectMapper;
    
    private JacksonSingletonEnum() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
    }
    
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
