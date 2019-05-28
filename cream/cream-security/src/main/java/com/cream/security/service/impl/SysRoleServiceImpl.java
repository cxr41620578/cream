/**
 * 
 */
package com.cream.security.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cream.core.base.service.impl.BaseServiceImpl;
import com.cream.security.entity.SysRole;
import com.cream.security.repository.ISysRoleRepository;
import com.cream.security.service.ISysRoleService;

/**
 * @author cream
 *
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<ISysRoleRepository, SysRole, Long> implements ISysRoleService {

    @Override
    public List<SysRole> findPermissionAll() {
        return baseRepository.findPermissionAll();
    }
}
