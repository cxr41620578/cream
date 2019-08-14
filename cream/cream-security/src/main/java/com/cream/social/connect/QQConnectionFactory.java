/**
 * 
 */
package com.cream.social.connect;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.util.Assert;

import com.cream.social.api.QQ;
import com.cream.social.connect.support.QQOAuth2Connection;
import com.cream.social.oauth2.QQAccessGrant;
import com.cream.social.oauth2.QQOAuth2ServiceProvider;

/**
 * @author cream
 *
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

//    public QQConnectionFactory(String appId, String appSecret) {
//        super("qq", new QQOAuth2ServiceProvider(appId, appSecret), new QQAdapter());
//    }
//    
//    @Override
//    protected String extractProviderUserId(AccessGrant accessGrant) {
//        return ((QQAccessGrant) accessGrant).getOpenid();
//    }
    
    public QQConnectionFactory(String appId, String appSecret) {
        super("qq", new QQOAuth2ServiceProvider(appId, appSecret), new QQAdapter());
    }
    
    protected String extractProviderUserId(AccessGrant accessGrant) {
        Assert.isInstanceOf(QQAccessGrant.class, accessGrant);
        return ((QQAccessGrant) accessGrant).getOpenid();
    }
    
    @Override
    public Connection<QQ> createConnection(AccessGrant accessGrant) {
        return new QQOAuth2Connection(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), (QQOAuth2ServiceProvider) getServiceProvider(), getApiAdapter());
    }
    
    @Override
    public Connection<QQ> createConnection(ConnectionData data) {
        return new QQOAuth2Connection(data, (QQOAuth2ServiceProvider) getServiceProvider(), getApiAdapter());
    }
}
