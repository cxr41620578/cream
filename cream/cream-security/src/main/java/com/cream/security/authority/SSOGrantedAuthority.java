/**
 * 
 */
package com.cream.security.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import com.cream.core.CreamVersion;

/**
 * @author cream
 *
 */
public final class SSOGrantedAuthority implements GrantedAuthority {
    
    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    private final String url;
    
    private final String method;
    
    public SSOGrantedAuthority(String url, String method) {
        Assert.hasText(url, "A granted authority textual representation is required");
        Assert.hasText(method, "A granted authority textual representation is required");
        this.url = url;
        this.method = method;
    }

    @Override
    public String getAuthority() {
        return this.url + "[" + this.method + "]";
    }
}
