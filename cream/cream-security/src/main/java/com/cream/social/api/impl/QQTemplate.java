/**
 * 
 */
package com.cream.social.api.impl;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

import com.cream.social.api.QQ;
import com.cream.social.api.UserOperations;

/**
 * @author cream
 *
 */
public class QQTemplate extends AbstractOAuth2ApiBinding implements QQ {
    
    private UserOperations userOperations;
    
    public QQTemplate (String accessToken) {
        super(accessToken);
        initSubApis();
    }

    public String getBaseGraphApiUrl() {
        return "https://graph.qq.com/";
    }

    @Override
    public UserOperations userOperations() {
        return this.userOperations;
    }
    
    private void initSubApis() {
        this.userOperations = new QQUserTemplate(this.getRestTemplate());
    }
}
