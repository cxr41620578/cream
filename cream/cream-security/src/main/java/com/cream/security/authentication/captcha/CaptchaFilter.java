/**
 * 
 */
package com.cream.security.authentication.captcha;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author v-chenxr04
 *
 */
public final class CaptchaFilter extends OncePerRequestFilter {
    
    private CaptchaService captchaService;
    private RequiresCaptchaAuthenticationService requiresCaptchaAuthenticationService;

    private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();
    
    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";
    
    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;
    
    public CaptchaFilter(RequiresCaptchaAuthenticationService requiresCaptchaAuthenticationService, CaptchaService captchaService) {
        Assert.notNull(requiresCaptchaAuthenticationService, "requiresCaptchaAuthenticationService cannot be null");
        Assert.notNull(captchaService, "captchaService cannot be null");
        this.requiresCaptchaAuthenticationService = requiresCaptchaAuthenticationService;
        this.captchaService = captchaService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (requiresCaptchaAuthenticationService.requiresCaptchaAuthentication(request)) {
            String captcha = captchaService.loadCaptacha(request);
            
            if (captcha == null) {
                accessDeniedHandler.handle(request, response, new MissingCaptchaException(captcha));
            } else {
                String actualCaptcha = obtainCaptcha(request);
                if (!captcha.equals(actualCaptcha)) {
                    accessDeniedHandler.handle(request, response, new InvalidCaptchaException(captcha));
                } else {
                    captchaService.removeCaptcha(request);
                }
            }
        }
        
        filterChain.doFilter(request, response);
    }

    protected String obtainCaptcha(HttpServletRequest request) {
        return request.getParameter(captchaParameter);
    }

    public CaptchaService getCaptchaService() {
        return captchaService;
    }

    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }
}
