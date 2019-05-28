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

import com.cream.core.CreamVersion;
import com.cream.core.base.entity.DataEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author cream
 *
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
public class SysRole extends DataEntity {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    /**
     * 角色名称
     */
    @Column(nullable = false, length = 60)
    private String roleName;
    
    /**
     * 角色描述
     */
    @Column(length = 255)
    private String roleDescritpion;
    
    /**
     * 用户和角色
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role",
            joinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id")
            })
    private Set<SysUser> sysUsers;
    
    /**
     * 角色和权限
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_permission",
            joinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "permission_id", referencedColumnName = "id")
            })
    private Set<SysPermission> sysPermissions;
}
