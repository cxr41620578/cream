/**
 * 
 */
package com.cream.security.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.cream.core.CreamVersion;
import com.cream.core.base.entity.DataEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 权限表
 * 
 * @author cream
 *
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
public class SysPermission extends DataEntity {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    /**
     * 权限名称
     */
    @Column(nullable = false, length = 60)
    private String permissionName;

    /**
     * 权限描述
     */
    @Column(length = 255)
    private String permissionDescritpion;
    
    /**
     * 资源路径
     */
    @Column(nullable = false, length = 255)
    private String httpUrl;
    
    /**
     * 资源路径请求方法
     */
    @Column(nullable = false, length = 6)
    private String httpMethod;

    /**
     * 资源
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private SysResource sysResources;

    /**
     * 操作
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_id")
    private SysOperation sysOperations;
    
    /**
     * 角色集
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_permission",
            joinColumns = {
                    @JoinColumn(name = "permission_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id")
            })
    private Set<SysRole> sysRoles;
}
