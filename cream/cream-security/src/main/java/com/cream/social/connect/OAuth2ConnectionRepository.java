/**
 * 
 */
package com.cream.social.connect;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cream.social.entity.SysUserConnection;
import com.cream.social.entity.SysUserConnectionKey;
import com.cream.social.projection.ISelectFromUserConnection;
import com.cream.social.service.ISysUserConnectionService;

/**
 * @author cream
 *
 */
public class OAuth2ConnectionRepository implements ConnectionRepository {
    
    private final Long userId;
    
    private final ConnectionFactoryLocator connectionFactoryLocator;
    
    private final ISysUserConnectionService sysUserConnectionService;
    
    private final TextEncryptor textEncryptor;
    
    public OAuth2ConnectionRepository(Long userId, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor, ISysUserConnectionService sysUserConnectionService) {
        this.userId = userId;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
        this.sysUserConnectionService = sysUserConnectionService;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {
        List<Connection<?>> resultList = projectedPayloadsToConnections(sysUserConnectionService.findAllByUserId(userId));
        MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
        Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
        for (String registeredProviderId : registeredProviderIds) {
            connections.put(registeredProviderId, Collections.<Connection<?>>emptyList());
        }
        for (Connection<?> connection : resultList) {
            String providerId = connection.getKey().getProviderId();
            if (connections.get(providerId).size() == 0) {
                connections.put(providerId, new LinkedList<Connection<?>>());
            }
            connections.add(providerId, connection);
        }
        return connections;
    }

    @Override
    public List<Connection<?>> findConnections(String providerId) {
        return projectedPayloadsToConnections(sysUserConnectionService.findByUserIdAndProviderId(userId, providerId));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        List<?> connections = findConnections(getProviderId(apiType));
        return (List<Connection<A>>) connections;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {
        Assert.notEmpty(providerUserIds, "Unable to execute find: no providerUsers provided");
        return null;
    }

    @Override
    public Connection<?> getConnection(ConnectionKey connectionKey) {
        return (Connection<?>) sysUserConnectionService.getById(
                SysUserConnectionKey.builder()
                .providerId(connectionKey.getProviderId())
                .providerUserId(connectionKey.getProviderUserId())
                .userId(userId)
                .build());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
        if (connection == null) {
            throw new NotConnectedException(providerId);
        }
        return connection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) findPrimaryConnection(providerId);
    }

    @Override
    public void addConnection(Connection<?> connection) {
        try {
            ConnectionData data = connection.createData();
            SysUserConnection sysUserConnection = SysUserConnection.builder()
            .userId(userId)
            .providerId(data.getProviderId())
            .providerUserId(data.getProviderUserId())
            .displayName(data.getDisplayName())
            .profileUrl(data.getProfileUrl())
            .imageUrl(data.getImageUrl())
            .accessToken(data.getAccessToken())
            .secret(encrypt(data.getSecret()))
            .refreshToken(encrypt(data.getRefreshToken()))
            .expireTime(data.getExpireTime())
            .build();
            sysUserConnectionService.insertOne(sysUserConnection);
        } catch (DuplicateKeyException e) {
            throw new DuplicateConnectionException(connection.getKey());
        }
    }

    @Override
    public void updateConnection(Connection<?> connection) {
        ConnectionData data = connection.createData();
        SysUserConnection sysUserConnection = SysUserConnection.builder()
        .userId(userId)
        .providerId(data.getProviderId())
        .providerUserId(data.getProviderUserId())
        .displayName(data.getDisplayName())
        .profileUrl(data.getProfileUrl())
        .imageUrl(data.getImageUrl())
        .accessToken(data.getAccessToken())
        .secret(encrypt(data.getSecret()))
        .refreshToken(encrypt(data.getRefreshToken()))
        .expireTime(data.getExpireTime())
        .build();
        sysUserConnectionService.updateConnectionById(sysUserConnection);
    }

    @Override
    public void removeConnections(String providerId) {
        sysUserConnectionService.deleteByUserIdAndProviderId(userId, providerId);
    }

    @Override
    public void removeConnection(ConnectionKey connectionKey) {
        SysUserConnectionKey sysUserConnectionKey = SysUserConnectionKey.builder().userId(userId)
        .providerId(connectionKey.getProviderId())
        .providerUserId(connectionKey.getProviderUserId())
        .build();
        sysUserConnectionService.deleteById(sysUserConnectionKey);
    }

    private Long expireTime(long expireTime) {
        return expireTime == 0 ? null : expireTime;
    }
    
    private String decrypt(String encryptedText) {
        return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
    }

    private String encrypt(String text) {
        return text != null ? textEncryptor.encrypt(text) : text;
    }
    
    private List<Connection<?>> projectedPayloadsToConnections(List<ISelectFromUserConnection> userConnections) {
        return userConnections.stream().map(v -> {
            ConnectionData connectionData = new ConnectionData(v.getProviderId(), v.getProviderUserId(), v.getDisplayName(), v.getProfileUrl(), v.getImageUrl(),
                    decrypt(v.getAccessToken()), decrypt(v.getSecret()), decrypt(v.getRefreshToken()), expireTime(v.getExpireTime()));
            ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
            return connectionFactory.createConnection(connectionData);
        }).collect(Collectors.toList());
    }

    private <A> String getProviderId(Class<A> apiType) {
        return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
    }

    private Connection<?> findPrimaryConnection(String providerId) {
        List<Connection<?>> connections = projectedPayloadsToConnections(sysUserConnectionService.findByUserIdAndProviderId(userId, providerId));
        if (connections.size() > 0) {
            return connections.get(0);
        } else {
            return null;
        }       
    }
}
