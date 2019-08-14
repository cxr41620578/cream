/**
 * 
 */
package com.cream.social.api.impl;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.support.HttpRequestDecorator;

/**
 * @author v-chenxr04
 *
 */
class QQParameterRequestInterceptor implements ClientHttpRequestInterceptor {
    
    private final String openid;
    
    private final String oauthConsumerKey;
    
    public QQParameterRequestInterceptor(String openid, String oauthConsumerKey) {
        this.openid = openid;
        this.oauthConsumerKey = oauthConsumerKey;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        HttpRequestDecorator protectedResourceRequest = new HttpRequestDecorator(request);
        protectedResourceRequest.addParameter("openid", openid);
        protectedResourceRequest.addParameter("oauth_consumer_key", oauthConsumerKey);
        return execution.execute(protectedResourceRequest, body);
    }
}
