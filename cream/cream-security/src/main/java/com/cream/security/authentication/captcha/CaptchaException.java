/**
 * 
 */
package com.cream.security.authentication.captcha;

import org.springframework.security.access.AccessDeniedException;

import com.cream.core.CreamVersion;

/**
 * @author v-chenxr04
 *
 */
public class CaptchaException extends AccessDeniedException {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    public CaptchaException(String msg) {
        super(msg);
    }
}
