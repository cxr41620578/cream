/**
 * 
 */
package com.cream.social.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Repository;

import com.cream.core.base.repository.IBaseRepository;
import com.cream.social.entity.SysUserConnection;

/**
 * @author cream
 *
 */
//public interface ISysUserConnectionRepository extends IBaseRepository<SysUserConnection, Long>, UsersConnectionRepository {

//    @Query("SELECT suc.sysUser.id FROM SysUserConnection suc WHERE suc.providerId = :#{#connection.providerId} AND suc.providerUserId = :#{#connection.providerUserId}")
//    List<String> findUserIdsWithConnection(Connection<?> connection);
    
//    @Query("SELECT suc.sysUser.id FROM SysUserConnection suc WHERE suc.providerId = :providerId AND suc.providerUserId IN (:providerUserIds})")
//    Set<String> findUserIdsConnectedTo(@Param("providerId") String providerId, @Param("providerUserIds") Set<String> providerUserIds);
    
//    ConnectionRepository createConnectionRepository(String userId);
//}
