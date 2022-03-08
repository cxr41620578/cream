/**
 * 
 */
package com.cream.security.service.impl;

import static com.cream.security.constant.CacheConstant.ROLE_ID_SET_OF_PERMISSION;

import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cream.core.base.service.impl.BaseServiceImpl;
import com.cream.security.entity.SysPermission;
import com.cream.security.repository.ISysPermissionRepository;
import com.cream.security.service.ISysPermissionService;

/**
 * @author cream
 *
 */
@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<ISysPermissionRepository, SysPermission, Long>
        implements ISysPermissionService {

    @Cacheable(value = ROLE_ID_SET_OF_PERMISSION, key = "#httpUrl.concat(':').concat(#httpMethod)")
    @Override
    public Set<Long> findRoleIdByHttpUrlAndHttpMethod(String httpUrl, String httpMethod) {
        return baseRepository.findRoleIdByHttpUrlAndHttpMethod(httpUrl, httpMethod);
    }

    public List<SysPermission> findRoleAll() {
        return baseRepository.findRoleAll();
    }

    @Override
    public Set<Long> findAllRoleId() {
        return baseRepository.findAllRoleId();
    }
}
