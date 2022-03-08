/**
 * 
 */
package com.cream.social.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.UserIdSource;
import org.springframework.social.connect.UsersConnectionRepository;

import pers.cream.spring.social.config.QQApiHelper;

/**
 * @author c-chenxr
 *
 */
@Configuration
public class QQConfig {

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private UserIdSource userIdSource;
    
    @Bean
    public QQApiHelper qqApiHelper() {
        return new QQApiHelper(usersConnectionRepository, userIdSource);
    }
}
