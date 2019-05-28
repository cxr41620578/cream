/**
 * 
 */
package com.cream.core.base;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author cream
 *
 */
public enum ResponseStatusEnum {

    SUCCESS(1),
    ERROR(0);
    
    private int status;
    
    ResponseStatusEnum(int status) {
        this.status = status;
    }
    
    @JsonValue
    public int getStatus() {
        return status;
    }
    
}
