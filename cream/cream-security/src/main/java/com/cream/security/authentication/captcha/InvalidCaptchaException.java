/**
 * 
 */
package com.cream.security.authentication.captcha;

import com.cream.core.CreamVersion;

/**
 * @author v-chenxr04
 *
 */
public class InvalidCaptchaException extends CaptchaException {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    public InvalidCaptchaException(String msg) {
        super(msg);
    }

}
