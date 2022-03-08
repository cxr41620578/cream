/**
 * 
 */
package com.cream.social.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import com.cream.social.connect.OAuth2UsersConnectionRepository;
import com.cream.social.service.ISysUserConnectionService;

import pers.cream.spring.social.connect.QQConnectionFactory;

/**
 * @author cream
 *
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private ISysUserConnectionService sysUserConnectionService;

    @Autowired
    private ConnectionSignUp connectionSignUp;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
            Environment environment) {
        connectionFactoryConfigurer.addConnectionFactory(new QQConnectionFactory(
                environment.getProperty("spring.social.qq.clientId"),
                environment.getProperty("spring.social.qq.clientSecret")
        ));
    }
    
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        OAuth2UsersConnectionRepository oauth2UsersConnectionRepository = new OAuth2UsersConnectionRepository(connectionFactoryLocator, Encryptors.noOpText(), sysUserConnectionService);
        oauth2UsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        return oauth2UsersConnectionRepository;
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }
}
