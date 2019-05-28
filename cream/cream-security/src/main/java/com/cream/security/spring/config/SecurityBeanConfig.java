/**
 * 
 */
package com.cream.security.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

/**
 * @author cream
 *
 */
@Configuration
public class SecurityBeanConfig {

    /**
     * spring session remember-me
     * 
     * @return
     */
    @Bean
    public RememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }
}
