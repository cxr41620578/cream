/**
 * 
 */
package com.cream.social.service;

import java.util.List;
import java.util.Set;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import com.cream.core.base.service.IBaseService;
import com.cream.social.entity.SysUserConnection;
import com.cream.social.entity.SysUserConnectionKey;
import com.cream.social.projection.ISelectFromUserConnection;

/**
 * @author v-chenxr04
 *
 */
public interface ISysUserConnectionService extends IBaseService<SysUserConnection, SysUserConnectionKey> {

    List<String> connectionByUserIds(Connection<?> connection, ConnectionSignUp connectionSignUp);
    
    Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds);
    
    /**
     * 查询用户下所有社交链接信息
     * @param userId
     * @return
     */
    List<ISelectFromUserConnection> findAllByUserId(Long userId);
    
    /**
     * 查询用户下某个社交链接信息
     * @param userId
     * @param providerId
     * @return
     */
    List<ISelectFromUserConnection> findByUserIdAndProviderId(Long userId, String providerId);
    
    /**
     * 根据主键查询数据
     * @param sysUserConnectionKey
     */
    ISelectFromUserConnection getById(SysUserConnectionKey sysUserConnectionKey);
    
    /**
     * 新增一条数据
     * @param sysUserConnection
     */
    void insertOne(SysUserConnection sysUserConnection);
    
    /**
     * 修改一条数据
     * @param sysUserConnection
     */
    void updateConnectionById(SysUserConnection sysUserConnection);
    
    /**
     * 删除某用户下的某个社交信息
     * @param userId
     * @param providerId
     */
    void deleteByUserIdAndProviderId(Long userId, String providerId);
}
