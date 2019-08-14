/**
 * 
 */
package com.cream.social.oauth2;

/**
 * @author v-chenxr04
 *
 */
public class QQOpenid {

    private final String clientId;
    
    private final String openid;
    
    public QQOpenid(String clientId, String openid) {
        this.clientId = clientId;
        this.openid = openid;
    }

    public String getClientId() {
        return clientId;
    }

    public String getOpenid() {
        return openid;
    }
}
