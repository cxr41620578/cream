/**
 * 
 */
package com.cream.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

/**
 * 身份认证过滤器
 * 
 * @author cream
 *
 */
public class SSOAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;
    private boolean postOnly = true;

    public SSOAuthenticationFilter(String loginUrl, String method) {
        super(new AntPathRequestMatcher(loginUrl, method));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String captcha = obtainCaptcha(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        if (captcha == null) {
            captcha = "";
        }

        username = username.trim();

        SSOAuthenticationToken authRequest = new SSOAuthenticationToken(username, password, captcha);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    protected String obtainCaptcha(HttpServletRequest request) {
        return request.getParameter(captchaParameter);
    }

    protected void setDetails(HttpServletRequest request, SSOAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public void setCaptchaParameter(String captchaParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.captchaParameter = captchaParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return usernameParameter;
    }

    public final String getPasswordParameter() {
        return passwordParameter;
    }

    public final String getCaptchaParameter() {
        return captchaParameter;
    }
}
