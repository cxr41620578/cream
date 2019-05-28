/**
 * 
 */
package com.cream.social.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author cream
 *
 */
@Configuration
public class SSOSpringSocialConfigurer extends SpringSocialConfigurer {

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        return (T) filter;
    }
}
