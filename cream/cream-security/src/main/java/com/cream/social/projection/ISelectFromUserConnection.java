/**
 * 
 */
package com.cream.social.projection;

import org.springframework.data.web.ProjectedPayload;

/**
 * @author v-chenxr04
 *
 */
@ProjectedPayload
public interface ISelectFromUserConnection {

    public Long getUserId();
    
    public String getProviderId();
    
    public String getProviderUserId();
    
    public String getDisplayName();
    
    public String getProfileUrl();
    
    public String getImageUrl();
    
    public String getAccessToken();
    
    public String getSecret();
    
    public String getRefreshToken();
    
    public Long getExpireTime();
}
