/**
 * 
 */
package com.cream.security.userdetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.cream.core.CreamVersion;

/**
 * @author cream
 *
 */
public class SSOClientDetails implements ClientDetails {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    private String clientId;
    
    private Set<String> resourceIds;

    private String clientSecret;
    
    private Set<String> scope;
    
    private Set<String> authorizedGrantTypes;
    
    private Set<String> registeredRedirectUri;
    
    private Collection<GrantedAuthority> authorities;
    
    private Integer accessTokenValiditySeconds;
    
    private Integer refreshTokenValiditySeconds;
    
    private Map<String, Object> additionalInformation;
    
    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return this.resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return false;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        return this.scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.registeredRedirectUri;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return this.additionalInformation;
    }

}
