/**
 * 
 */
package com.cream.social.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

import com.cream.social.api.QQ;
import com.cream.social.api.impl.QQTemplate;
import com.cream.social.oauth2.QQOAuth2Template;

/**
 * @author cream
 *
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    
    private static final String GRAPH_API_URL = "https://graph.qq.com/oauth2.0/";

    public QQServiceProvider(String appId, String appSecret) {
        super(getOAuth2Template(appId, appSecret));
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQTemplate(accessToken);
    }
    
    private static OAuth2Template getOAuth2Template(String appId, String appSecret) {
        QQOAuth2Template oAuth2Template = new QQOAuth2Template(appId, appSecret,
                GRAPH_API_URL + "authorize",
                GRAPH_API_URL + "token");
        return oAuth2Template;
    }
}
