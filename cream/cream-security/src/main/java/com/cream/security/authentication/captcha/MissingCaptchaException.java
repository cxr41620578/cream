/**
 * 
 */
package com.cream.security.authentication.captcha;

import com.cream.core.CreamVersion;

/**
 * @author v-chenxr04
 *
 */
public class MissingCaptchaException extends CaptchaException {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    public MissingCaptchaException(String msg) {
        super(msg);
    }
}
