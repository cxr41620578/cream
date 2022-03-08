/**
 * 
 */
package com.cream.social.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

import com.cream.core.base.service.impl.BaseServiceImpl;
import com.cream.security.entity.SysUser;
import com.cream.social.entity.SysUserConnection;
import com.cream.social.entity.SysUserConnectionKey;
import com.cream.social.projection.ISelectFromUserConnection;
import com.cream.social.repository.ISysUserConnectionRepository;
import com.cream.social.service.ISysUserConnectionService;

/**
 * @author v-chenxr04
 *
 */
@Service
public class SysUserConnectionServiceImpl extends BaseServiceImpl<ISysUserConnectionRepository, SysUserConnection, SysUserConnectionKey> implements ISysUserConnectionService {
    
    @Override
    public List<String> connectionByUserIds(Connection<?> connection, ConnectionSignUp connectionSignUp) {
        List<String> userIdList = baseRepository.findByProviderIdAndProviderUserId(connection.getKey().getProviderId(), connection.getKey().getProviderUserId());
        if (CollectionUtils.isEmpty(userIdList) && connectionSignUp != null) {
            String newUserId = connectionSignUp.execute(connection);
            if (newUserId != null) {
                SysUserConnection sysUserConnection = new SysUserConnection();
                sysUserConnection.setProviderId(connection.getKey().getProviderId());
                sysUserConnection.setProviderUserId(connection.getKey().getProviderUserId());
                sysUserConnection.setUserId(Long.valueOf(newUserId));
                ConnectionData connectionData = connection.createData();
                sysUserConnection.setAccessToken(connectionData.getAccessToken());
                baseRepository.insertOne(sysUserConnection);
                return Arrays.asList(newUserId);
            }
        }
        return userIdList;
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        return baseRepository.findByProviderIdAndProviderUserIdIn(providerId, providerUserIds)
                .stream().collect(Collectors.toSet());
    }

    @Override
    public List<ISelectFromUserConnection> findAllByUserId(Long userId) {
        return baseRepository.findByUserIdOrderByProviderIdAscRank(userId);
    }

    @Override
    public List<ISelectFromUserConnection> findByUserIdAndProviderId(Long userId, String providerId) {
        return baseRepository.findByUserIdAndProviderIdOrderByRank(userId, providerId);
    }

    @Override
    public ISelectFromUserConnection getById(SysUserConnectionKey sysUserConnectionKey) {
        return baseRepository.findPayloadByUserIdAndProviderIdAndProviderUserId(sysUserConnectionKey.getUserId(), sysUserConnectionKey.getProviderId(), sysUserConnectionKey.getProviderUserId());
    }

    @Override
    public void insertOne(SysUserConnection sysUserConnection) {
        baseRepository.insertOne(sysUserConnection);
    }

    @Override
    public void updateConnectionById(SysUserConnection sysUserConnection) {
        baseRepository.updateConnectionById(sysUserConnection);
    }

    @Override
    public void deleteByUserIdAndProviderId(Long userId, String providerId) {
        baseRepository.deleteByUserIdAndProviderId(userId, providerId);
    }
}
