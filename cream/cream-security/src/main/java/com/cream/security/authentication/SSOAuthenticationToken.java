/**
 * 
 */
package com.cream.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @author cream
 *
 */
public class SSOAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String captcha;

    public SSOAuthenticationToken(Object principal, Object credentials, String captcha) {
        super(principal, credentials);
        this.captcha = captcha;
    }

    public SSOAuthenticationToken(Object principal, Object credentials, String captcha,
            Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return this.captcha;
    }
}
