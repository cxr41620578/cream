/**
 * 
 */
package com.cream.social.connect;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;

import com.cream.social.service.ISysUserConnectionService;

/**
 * @author cream
 *
 */
public class OAuth2UsersConnectionRepository implements UsersConnectionRepository {
    
    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;

    private ConnectionSignUp connectionSignUp;
    
    private ISysUserConnectionService sysUserConnectionService;
    
    public OAuth2UsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor, ISysUserConnectionService sysUserConnectionService) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
        this.sysUserConnectionService = sysUserConnectionService;
    }

    public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
        this.connectionSignUp = connectionSignUp;
    }

    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        return sysUserConnectionService.connectionByUserIds(connection, connectionSignUp);
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        return sysUserConnectionService.findUserIdsConnectedTo(providerId, providerUserIds);
    }

    @Override
    public ConnectionRepository createConnectionRepository(String userId) {
        return new OAuth2ConnectionRepository(Long.valueOf(userId), connectionFactoryLocator, textEncryptor, sysUserConnectionService);
    }
}
