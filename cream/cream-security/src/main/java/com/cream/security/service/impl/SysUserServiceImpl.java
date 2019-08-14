/**
 * 
 */
package com.cream.security.service.impl;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.cream.core.base.service.impl.BaseServiceImpl;
import com.cream.security.entity.SysUser;
import com.cream.security.projection.UserPayload;
import com.cream.security.repository.ISysUserRepository;
import com.cream.security.service.ISysUserService;

/**
 * @author cream
 *
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<ISysUserRepository, SysUser, Long> implements ISysUserService {

    @Override
    public UserPayload findByUsername(String username) {
        return baseRepository.findByUsername(username);
    }
    
    @Override
    public UserPayload getById(Long id) {
        return baseRepository.getById(id);
    }

    @Override
    public Set<Long> findRoleIdsByUsername(String username) {
        return baseRepository.findRoleIdsByUsername(username);
    }

    @Override
    public void register(SysUser sysUser) {
        baseRepository.save(sysUser);
    }

    @Override
    public Set<Long> findRoleIdsById(Long id) {
        return baseRepository.findRoleIdsById(id);
    }
}
