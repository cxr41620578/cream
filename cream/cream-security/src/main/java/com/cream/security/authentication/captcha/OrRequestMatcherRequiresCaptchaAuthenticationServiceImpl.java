/**
 * 
 */
package com.cream.security.authentication.captcha;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.util.Assert;

/**
 * @author v-chenxr04
 *
 */
public class OrRequestMatcherRequiresCaptchaAuthenticationServiceImpl implements RequiresCaptchaAuthenticationService {
    
    private OrRequestMatcher requiresCaptchaAuthentications;
    
    public OrRequestMatcherRequiresCaptchaAuthenticationServiceImpl(OrRequestMatcher requiresCaptchaAuthentications) {
        Assert.notNull(requiresCaptchaAuthentications, "requiresCaptchaAuthentications cannot be null");
        this.requiresCaptchaAuthentications = requiresCaptchaAuthentications;
    }

    @Override
    public boolean requiresCaptchaAuthentication(HttpServletRequest request) {
        return requiresCaptchaAuthentications.matches(request);
    }
}
