/**
 * 
 */
package com.cream.security.authentication.captcha;

import javax.servlet.http.HttpServletRequest;

/**
 * @author v-chenxr04
 *
 */
public interface RequiresCaptchaAuthenticationService {

    boolean requiresCaptchaAuthentication(HttpServletRequest request);
}
