/**
 * 
 */
package com.cream.social.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cream.core.CreamVersion;
import com.cream.core.base.entity.BaseEntity;
import com.cream.core.base.enums.ProviderEnum;
import com.cream.security.entity.SysUser;

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
public class SysUserConnection extends BaseEntity {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    /**
     * 用户ID
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private SysUser sysUser;
    
    /**
     * 服务提供商
     */
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Integer providerId;
    
    /**
     * 服务提供商返回的用户唯一标识
     */
    @Column(nullable = false)
    private ProviderEnum providerUserId;
}
