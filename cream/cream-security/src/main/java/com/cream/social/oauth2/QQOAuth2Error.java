/**
 * 
 */
package com.cream.social.oauth2;

/**
 * @author v-chenxr04
 *
 */
public class QQOAuth2Error {

    private final Integer error;
    
    private final String errorDescription;
    
    public QQOAuth2Error(Integer error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public Integer getError() {
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
