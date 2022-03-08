/**
 * 
 */
package com.cream.security.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cream.core.base.repository.IBaseRepository;
import com.cream.security.entity.SysUser;
import com.cream.security.projection.UserPayload;

/**
 * @author cream
 *
 */
@Repository
public interface ISysUserRepository extends IBaseRepository<SysUser, Long> {

    /**
     * 根据用户名查找用户及角色ID
     * @param username
     * @return
     */
    UserPayload findByUsername(String username);

    /**
     * 根据用户主键查找用户信息
     * @param <T>
     * @param id
     * @param clazz
     * @return
     */
    <T> T getById(Long id, Class<T> clazz);

    /**
     * 根据用户名查找角色id集合
     * @param username
     * @return
     */
    @Query("SELECT sr.id FROM SysUser su INNER JOIN su.sysRoles sr WHERE su.username = ?1")
    Set<Long> findRoleIdsByUsername(String username);

    @Query("SELECT sr.id FROM SysUser su INNER JOIN su.sysRoles sr WHERE su.id = ?1")
    Set<Long> findRoleIdsById(Long id);
}
