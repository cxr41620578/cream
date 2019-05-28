/**
 * 
 */
package com.cream.social.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author cream
 *
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.social.qq")
public class SocialProperties {

    private String clientId;
    
    private String secret;
}
