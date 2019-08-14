/**
 * 
 */
package com.cream.social.oauth2;

import org.springframework.social.oauth2.AccessGrant;

import com.cream.core.CreamVersion;

/**
 * @author v-chenxr04
 *
 */
public class QQAccessGrant extends AccessGrant {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    private final String openid;
    
    public QQAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String openid) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.openid = openid;
    }
    
    public String getOpenid() {
        return openid;
    }
}
