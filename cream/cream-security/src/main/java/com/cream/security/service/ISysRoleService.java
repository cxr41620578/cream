/**
 * 
 */
package com.cream.security.service;

import java.util.List;

import com.cream.core.base.service.IBaseService;
import com.cream.security.entity.SysRole;

/**
 * @author cream
 *
 */
public interface ISysRoleService extends IBaseService<SysRole, Long> {

    List<SysRole> findPermissionAll();
}
