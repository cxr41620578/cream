/**
 * 
 */
package com.cream.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

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
//@Entity
public class OauthClientDetails extends DataEntity {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    /**
     * 客户端标识
     */
    @Column(nullable = false)
    private Long clientId;
    
    @Column
    private String resourceIds;

    @Column
    private String clientSecret;
    
    @Column
    private String scope;
    
    @Column
    private String authorizedGrantTypes;
    
    @Column
    private String webServerRedirectUri;
    
    @Column
    private String authorities;
    
    @Column
    private Integer accessTokenValidity;
    
    @Column
    private Integer refreshTokenValidity;
    
    @Column
    private String additionalInformation;
    
    @Column
    private String autoapprove;
}
