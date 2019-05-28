/**
 * 
 */
package com.cream.security.service;

import java.util.Set;

import com.cream.core.base.service.IBaseService;
import com.cream.security.entity.SysUser;
import com.cream.security.projection.UserPayload;

/**
 * @author cream
 *
 */
public interface ISysUserService extends IBaseService<SysUser, Long> {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    UserPayload findByUsername(String username);
    
    UserPayload getById(Long id);
    
    /**
     * 根据用户名查找角色id集合
     * @param username
     * @return
     */
    Set<Long> findRoleIdsByUsername(String username);
    
    void register(SysUser sysUser);
}
