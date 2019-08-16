/**
 * 
 */
package com.cream.security.authentication.captcha;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.Assert;

/**
 * @author v-chenxr04
 *
 */
public class SpringSecurityCaptchaConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private CaptchaService captchaService = new HttpSessionCaptchaServiceImpl();
    private RequiresCaptchaAuthenticationService requiresCaptchaAuthenticationService = new NullRequiresCaptchaAuthenticationServiceImpl();
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        CaptchaFilter filter = new CaptchaFilter(requiresCaptchaAuthenticationService, captchaService);
        filter = postProcess(filter);
        http.addFilterAfter(filter, AbstractPreAuthenticatedProcessingFilter.class);
    }
    
    public SpringSecurityCaptchaConfigurer captchaService(CaptchaService captchaService) {
        Assert.notNull(captchaService, "captchaService cannot be null");
        this.captchaService = captchaService;
        return this;
    }

    public SpringSecurityCaptchaConfigurer requiresCaptchaAuthenticationService(RequiresCaptchaAuthenticationService requiresCaptchaAuthenticationService) {
        Assert.notNull(requiresCaptchaAuthenticationService, "requiresCaptchaAuthenticationService cannot be null");
        this.requiresCaptchaAuthenticationService = requiresCaptchaAuthenticationService;
        return this;
    }
}
