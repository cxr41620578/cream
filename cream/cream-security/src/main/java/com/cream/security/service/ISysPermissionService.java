/**
 * 
 */
package com.cream.security.service;

import java.util.List;
import java.util.Set;

import com.cream.core.base.service.IBaseService;
import com.cream.security.entity.SysPermission;

/**
 * @author cream
 *
 */
public interface ISysPermissionService extends IBaseService<SysPermission, Long> {
    
    Set<Long> findByUrlAndHttpMethod(String url, String httpMethod);

    List<SysPermission> findRoleAll();
}
