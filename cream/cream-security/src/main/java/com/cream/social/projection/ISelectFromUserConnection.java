/**
 * 
 */
package com.cream.social.projection;

import org.springframework.data.web.ProjectedPayload;

import com.cream.social.entity.SysUserConnection;

/**
 * @author v-chenxr04
 *
 */
@ProjectedPayload
public abstract class ISelectFromUserConnection extends SysUserConnection {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public abstract Long getUserId();
    
    public abstract String getProviderId();
    
    public abstract String getProviderUserId();
    
    public abstract String getDisplayName();
    
    public abstract String getProfileUrl();
    
    public abstract String getImageUrl();
    
    public abstract String getAccessToken();
    
    public abstract String getSecret();
    
    public abstract String getRefreshToken();
    
    public abstract Long getExpireTime();
}
