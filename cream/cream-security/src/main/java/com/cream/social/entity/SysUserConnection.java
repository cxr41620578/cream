/**
 * 
 */
package com.cream.social.entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.cream.core.CreamVersion;
import com.cream.core.base.entity.Entity;
import com.cream.security.entity.SysUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author cream
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@javax.persistence.Entity
@IdClass(SysUserConnectionKey.class)
public class SysUserConnection extends Entity {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    /**
     * 用户ID
     */
    @Id
    private Long userId;
    
    /**
     * 服务提供商
     */
    @Id
    private String providerId;
    
    /**
     * 服务提供商返回的用户唯一标识
     */
    @Id
    private String providerUserId;
    

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private SysUser sysUser;
    
    @Column(nullable = false)
    private Integer rank;
    
    @Column(length = 255)
    private String displayName;
    
    @Column(length = 512)
    private String profileUrl;
    
    @Column(length = 512)
    private String imageUrl;
    
    @Column(length = 255, nullable = false)
    private String accessToken;
    
    @Column(length = 255)
    private String secret;
    
    @Column(length = 255)
    private String refreshToken;
    
    @Column
    private Long expireTime;
}
