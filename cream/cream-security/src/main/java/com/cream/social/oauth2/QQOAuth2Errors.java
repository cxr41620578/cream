/**
 * 
 */
package com.cream.social.oauth2;

/**
 * @author v-chenxr04
 *
 */
public interface QQOAuth2Errors {

    int SUCCESS = 0;
    
    /**
     * 缺少参数response_type或response_type非法
     */
    int PARAM_RESPONSE_TYPE = 100000;
    
    /**
     * 缺少参数client_id
     */
    int PARAM_CLIENT_ID = 100001;
    
    /**
     * 缺少参数client_secret
     */
    int PARAM_CLIENT_SECRET = 100002;
    
    /**
     * http head中缺少Authorization
     */
    int PARAM_HEAD_AUTHORIZATION = 100003;
    
    /**
     * 缺少参数grant_type或grant_type非法
     */
    int PARAM_GRANT_TYPE = 100004;
    
    /**
     * 缺少参数code
     */
    int PARAM_CODE = 100005;
    
    /**
     * 该appid不存在
     */
    int PARAM_APPID = 100008;
    
    /**
     * client_secret（即appkey）非法
     */
    int PARAM_BAD_CLIENT_SECRET = 100009;
    
    /**
     * 回调地址不合法，常见原因请见：回调地址常见问题及修改方法
     */
    int PARAM_BAD_CALLBACK = 100010;
    
    /**
     * APP不处于上线状态
     */
    int APP_NOT_ONLINE = 100011;
    
    /**
     * HTTP请求非post方式
     */
    int HTTP_REQUEST_NON_POST = 100012;
}
