/**
 * 
 */
package com.cream.social.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.cream.social.api.QQ;

/**
 * @author cream
 *
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String appId, String appSecret) {
        super("qq", new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
