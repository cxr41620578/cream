/**
 * 
 */
package com.cream.social.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cream.core.base.repository.IBaseRepository;
import com.cream.social.entity.SysUserConnection;
import com.cream.social.entity.SysUserConnectionKey;
import com.cream.social.projection.ISelectFromUserConnection;

/**
 * @author v-chenxr04
 *
 */
@Repository
public interface ISysUserConnectionRepository extends IBaseRepository<SysUserConnection, SysUserConnectionKey> {

    @Query("SELECT userId FROM SysUserConnection WHERE providerId = :providerId AND providerUserId = :providerUserId")
    List<String> findByProviderIdAndProviderUserId(String providerId, String providerUserId);
    
    @Query("SELECT userId FROM SysUserConnection WHERE providerId = :providerId AND providerUserId IN (:providerUserIds)")
    List<String> findByProviderIdAndProviderUserIdIn(String providerId, Collection<String> providerUserIds);
    
    @Modifying
    @Query("INSERT INTO SysUserConnection(userId, providerId, providerUserId, rank, displayName, profileUrl, imageUrl, accessToken, secret, refreshToken, expireTime)"
            + " SELECT :#{#sysUserConnection.userId}, :#{#sysUserConnection.providerId}, :#{#sysUserConnection.providerUserId},"
            + " (SELECT coalesce(MAX(rank) + 1, 1)"
            + " FROM SysUserConnection"
            + " WHERE userId = :#{#sysUserConnection.userId}"
            + " AND providerId = :#{#sysUserConnection.providerId}), :#{#sysUserConnection.displayName},"
            + " :#{#sysUserConnection.profileUrl}, :#{#sysUserConnection.imageUrl},"
            + " :#{#sysUserConnection.accessToken}, :#{#sysUserConnection.secret},"
            + " :#{#sysUserConnection.refreshToken}, :#{#sysUserConnection.expireTime}"
            + " FROM Dual")
    void insertOne(SysUserConnection sysUserConnection);

    /**
     * 修改一条数据
     * @param sysUserConnection
     */
    @Modifying
    @Query("UPDATE SysUserConnection SET displayName = :#{#sysUserConnection.displayName},"
            + " profileUrl = :#{#sysUserConnection.profileUrl},"
            + " imageUrl = :#{#sysUserConnection.imageUrl},"
            + " accessToken = :#{#sysUserConnection.accessToken},"
            + " secret = :#{#sysUserConnection.secret},"
            + " refreshToken = :#{#sysUserConnection.refreshToken},"
            + " expireTime = :#{#sysUserConnection.expireTime}"
            + " WHERE userId = :#{#sysUserConnection.userId}"
            + " AND providerId = :#{#sysUserConnection.providerId}"
            + " AND providerUserId = :#{#sysUserConnection.providerUserId}")
    void updateConnectionById(SysUserConnection sysUserConnection);
    
    /**
     * 查询用户下所有社交链接信息
     * @param userId
     * @return
     */
    List<ISelectFromUserConnection> findByUserIdOrderByProviderIdAscRank(Long userId);

    /**
     * 查询用户下某个社交链接信息
     * @param userId
     * @param providerId
     * @return
     */
    List<ISelectFromUserConnection> findByUserIdAndProviderIdOrderByRank(Long userId, String providerId);
    
    /**
     * 根据主键查询
     * @param sysUserConnectionKey
     * @return
     */
//    @Query("FROM SysUserConnection WHERE userId = :#{#sysUserConnectionKey.userId} AND providerId = :#{#sysUserConnectionKey.providerId} AND providerUserId = :#{#sysUserConnectionKey.providerUserId}")
    ISelectFromUserConnection findPayloadByUserIdAndProviderIdAndProviderUserId(Long userId, String providerId, String providerUserId);
    
    @Query("DELETE FROM SysUserConnection WHERE userId = :userId AND providerId = :providerId")
    @Modifying
    void deleteByUserIdAndProviderId(Long userId, String providerId);
}