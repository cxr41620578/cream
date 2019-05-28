/**
 * 
 */
package com.cream.core.exception;

import com.cream.core.CreamVersion;

import lombok.Getter;

/**
 * @author cream
 *
 */
@Getter
public class BusinessException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    protected String code;
    
    protected String message;
    
    protected Object data;
    
    public BusinessException() {
        
    }
}
