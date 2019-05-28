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
public class SysUser extends DataEntity {
    
    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    /**
     * 用户名
     */
    @Column(nullable = false, length = 60)
    private String username;
    
    /**
     * 昵称
     */
    @Column(nullable = false, length = 60)
    private String nickname;
    
    /**
     * 密码
     */
    @Column(nullable = false, columnDefinition = "CHAR(60)")
    private String userPassword;
    
    /**
     * 电话
     */
    @Column(nullable = false, length = 20)
    private String phone;
    
    /**
     * 邮箱
     */
    @Column(nullable = false, length = 50)
    private String email;
    
    /**
     * 性别
     */
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private SexEnum sex;
    
    /**
     * 头像图片路径
     */
    @Column(nullable = false, length = 255)
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
    
    /**
     * 是否开启验证码验证
     */
    @Column
    private Boolean isTurnOnCaptcha;

    /**
     * 账户是否过期,过期无法验证
     */
    @Column
    private Boolean isAccountNonExpired;

    /**
     * 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
     */
    @Column
    private Boolean isAccountNonLocked;

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    @Column
    private Boolean isCredentialsNonExpired;

    /**
     * 是否被禁用,禁用的用户不能身份验证
     */
    @Column
    private Boolean isEnabled;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id")
            })
    private Set<SysRole> sysRoles;
}
