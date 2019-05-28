/**
 * 
 */
package com.cream.security.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * @author cream
 *
 */
@Configuration
public class RedisSessionConfig {

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        CookieHttpSessionIdResolver strategy = new CookieHttpSessionIdResolver();
        cookieSerializer.setCookieName("user_session");
        cookieSerializer.setCookieMaxAge(1800);
        strategy.setCookieSerializer(cookieSerializer);
        return strategy;
    }
}
