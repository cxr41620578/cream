/**
 * 
 */
package com.cream.security.authentication.captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author v-chenxr04
 *
 */
public class HttpSessionCaptchaServiceImpl implements CaptchaService {

    static final String DEFAULT_CAPTCHA_ATTR_NAME = "_captcha";

    private String sessionAttributeName = DEFAULT_CAPTCHA_ATTR_NAME;
    
    public String loadCaptacha(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (String) session.getAttribute(sessionAttributeName);
    }

    @Override
    public void saveCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute(this.sessionAttributeName, captcha);
    }

    @Override
    public void removeCaptcha(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(this.sessionAttributeName);
    }
}
