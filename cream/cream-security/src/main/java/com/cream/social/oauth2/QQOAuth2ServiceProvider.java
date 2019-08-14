/**
 * 
 */
package com.cream.social.oauth2;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;

import com.cream.social.api.QQ;
import com.cream.social.api.impl.QQTemplate;

/**
 * @author v-chenxr04
 *
 */
public class QQOAuth2ServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private static final String GRAPH_API_URL = "https://graph.qq.com/oauth2.0/";
    
    private final String appId;
    
    public QQOAuth2ServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret,
                GRAPH_API_URL + "authorize",
                GRAPH_API_URL + "token",
                GRAPH_API_URL + "me"));
        this.appId = appId;
    }

    public QQ getApi(String accessToken, String openid) {
        return new QQTemplate(accessToken, openid, appId);
    }

    @Override
    public QQ getApi(String accessToken) {
        throw new UnsupportedOperationException();
    }
}
