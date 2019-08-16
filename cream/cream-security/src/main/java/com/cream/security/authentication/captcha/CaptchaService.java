/**
 * 
 */
package com.cream.security.authentication.captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author v-chenxr04
 *
 */
public interface CaptchaService {

    String loadCaptacha(HttpServletRequest request);
    
    void saveCaptcha(String captcha, HttpServletRequest request,
            HttpServletResponse response);
    
    void removeCaptcha(HttpServletRequest request);
}
