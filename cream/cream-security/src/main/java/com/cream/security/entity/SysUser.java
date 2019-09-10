/**
 * 
 */
package com.cream.security.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.cream.core.CreamVersion;
import com.cream.core.base.entity.DataEntity;
import com.cream.core.base.enums.SexEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author cream
 *
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
public class SysUser extends DataEntity {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    /**
     * 用户名
     */
    @Column(length = 60)
    private String username;
    
    /**
     * 昵称
     */
    @Column(length = 60)
    private String nickname;
    
    /**
     * 密码
     */
    @Column(columnDefinition = "CHAR(60)")
    private String userPassword;
    
    /**
     * 电话
     */
    @Column(length = 20)
    private String phone;
    
    /**
     * 邮箱
     */
    @Column(length = 50)
    private String email;
    
    /**
     * 性别
     */
    @Column(columnDefinition = "TINYINT(1)")
    private SexEnum sex;
    
    /**
     * 头像图片路径
     */
    @Column(length = 255)
    private String headImgUrl;
    
    /**
     * 登录次数
     */
    @Column
    private Integer loginNum;
    
    /**
     * 最后登录时间
     */
    @Column
    private Date lastLoginDate;
    
    /**
     * 最后登录IP
     */
    @Column(length = 15)
    private String lastLoginIp;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sysUserRole",
            joinColumns = {
                    @JoinColumn(name = "userId", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "roleId", referencedColumnName = "id")
            })
    private Set<SysRole> sysRoles;
}
