/**
 * 
 */
package com.cream.security.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.cream.core.base.repository.IBaseRepository;
import com.cream.security.entity.QSysPermission;
import com.cream.security.entity.QSysRole;
import com.cream.security.entity.SysPermission;
import com.cream.security.entity.SysRole;

/**
 * @author cream
 *
 */
@Repository
public interface ISysRoleRepository extends IBaseRepository<SysRole, Long> {

    default List<SysRole> findPermissionAll() {
        QSysRole qSysRole = QSysRole.sysRole;
        QSysPermission qSysPermission = QSysPermission.sysPermission;
        return this.getJPAQueryFactory().select(qSysRole.id, qSysPermission.httpUrl, qSysPermission.httpMethod)
        .from(qSysRole)
        .innerJoin(qSysPermission)
        .fetch()
        .stream()
        .collect(Collectors.groupingBy(tuple -> tuple.get(qSysRole.id)))
        .entrySet()
        .stream()
        .map(m -> {
            SysRole sysRole = new SysRole();
            sysRole.setId(m.getKey());
            sysRole.setSysPermissions(m.getValue().stream().map(e -> {
                SysPermission sysPermission = new SysPermission();
                sysPermission.setHttpUrl(e.get(qSysPermission.httpUrl));
                sysPermission.setHttpMethod(e.get(qSysPermission.httpMethod));
                return sysPermission;
            }).collect(Collectors.toSet()));
            return sysRole;
        }).collect(Collectors.toList());
    }
}
