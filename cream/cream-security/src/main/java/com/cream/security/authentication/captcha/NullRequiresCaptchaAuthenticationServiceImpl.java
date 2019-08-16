/**
 * 
 */
package com.cream.security.authentication.captcha;

import javax.servlet.http.HttpServletRequest;

/**
 * @author v-chenxr04
 *
 */
public class NullRequiresCaptchaAuthenticationServiceImpl implements RequiresCaptchaAuthenticationService {

    @Override
    public boolean requiresCaptchaAuthentication(HttpServletRequest request) {
        return false;
    }

}
