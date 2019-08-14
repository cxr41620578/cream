/**
 * 
 */
package com.cream.social.api.impl;


import com.cream.social.api.GraphApi;
import com.cream.social.api.QQUser;
import com.cream.social.api.UserOperations;

/**
 * @author cream
 *
 */
class QQUserTemplate implements UserOperations {

    private final GraphApi graphApi;
    
    public QQUserTemplate(GraphApi graphApi) {
        this.graphApi = graphApi;
    }

    @Override
    public QQUser getUserInfo() {
        return graphApi.fetchObject("user/get_user_info", QQUser.class);
    }
}
