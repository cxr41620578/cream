/**
 * 
 */
package com.cream.security.exception;

import org.springframework.security.core.AuthenticationException;

import com.cream.core.CreamVersion;

/**
 * @author cream
 *
 */
public class BadCaptchaException extends AuthenticationException {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    public BadCaptchaException(String msg) {
        super(msg);
    }

    public BadCaptchaException(String msg, Throwable t) {
        super(msg, t);
    }
}
