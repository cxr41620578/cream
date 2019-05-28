/**
 * 
 */
package com.cream.social.api.impl;


import org.springframework.web.client.RestTemplate;

import com.cream.social.api.QQUser;
import com.cream.social.api.UserOperations;

/**
 * @author cream
 *
 */
class QQUserTemplate implements UserOperations {
    
    private final RestTemplate restTemplate;
    
    public QQUserTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public QQUser getUserInfo() {
        return restTemplate.getForObject("user/get_user_info", QQUser.class);
    }
}
