/**
 * 
 */
package com.cream.security.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author cream
 * @date 2017-11-06
 *
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.security")
@Component
public class SecurityConfig {

    /**
     * 登录路径
     */
    private String loginUrl;

    /**
     * 登录成功路径
     */
    private String loginSuccessUrl;

    /**
     * 登录错误路径
     */
    private String loginErrorUrl;

    /**
     * 登出路径
     */
    private String logoutUrl;

    /**
     * 登出成功路径
     */
    private String logoutSuccessUrl;
    
    /**
     * 登录失效路径
     */
    private String loginInvalidUrl;
    
    /**
     * 登录过期路径
     */
    private String loginInformationExpiredUrl;
    
    /**
     * 是否阻止新的登录
     */
    private boolean isMaxSessionsPreventsLogin;
    
    /**
     * 最大并发session
     */
    private int maximumSessions;
}
