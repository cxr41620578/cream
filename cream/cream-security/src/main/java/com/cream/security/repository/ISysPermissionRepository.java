/**
 * 
 */
package com.cream.security.repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.Query;
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
public interface ISysPermissionRepository extends IBaseRepository<SysPermission, Long> {
    
    /**
     * 根据路径和访问方法查询
     * @param httpUrl
     * @param httpMethod
     * @return
     */
    @Query("SELECT sr.id FROM SysPermission sp LEFT JOIN sp.sysRoles sr WHERE sp.httpUrl = ?1 AND sp.httpMethod = ?2")
    Set<Long> findRoleIdByHttpUrlAndHttpMethod(String httpUrl, String httpMethod);

    @Query("SELECT sr.id FROM SysPermission sp LEFT JOIN sp.sysRoles sr")
    Set<Long> findAllRoleId();

    default List<SysPermission> findRoleAll() {
        QSysRole qSysRole = QSysRole.sysRole;
        QSysPermission qSysPermission = QSysPermission.sysPermission;
//        QSysPermission qRole = new QSysPermission("sysRole");
        QSysRole qRole = new QSysRole("sysRole");
        return this.getJPAQueryFactory().select(qSysPermission.httpUrl, qSysPermission.httpMethod, qSysRole.id)
        .from(qSysPermission)
        .innerJoin(qSysPermission.sysRoles, qRole)
        .fetch()
        .stream()
        .collect(Collectors.groupingBy(tuple -> tuple.get(qSysPermission.httpMethod) + "_" + tuple.get(qSysPermission.httpUrl)))
        .entrySet()
        .stream()
        .map(m -> {
            SysPermission sysPermission = new SysPermission();
            String[] str = StringUtils.split(m.getKey(), "_", 2);
            sysPermission.setHttpMethod(str[0])
            .setHttpUrl(str[1])
            .setSysRoles(m.getValue().stream().map(e -> {
                SysRole sysRole = new SysRole();
                sysRole.setId(e.get(qSysRole.id));
                return sysRole;
            }).collect(Collectors.toSet()));
            return sysPermission;
        }).collect(Collectors.toList());
    }
}
