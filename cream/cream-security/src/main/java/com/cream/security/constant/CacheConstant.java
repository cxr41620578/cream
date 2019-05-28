/**
 * 
 */
package com.cream.security.constant;

/**
 * @author cream
 *
 */
public interface CacheConstant {

    String BASE_CAPTCHA = "user_captcha:";
    
    String USER_CAPTCHA = BASE_CAPTCHA + "captcha:";
    
    String USER_CAPTCHA_EXPIRES = USER_CAPTCHA + "expires:";
    
    String ROLE_ID_SET_OF_PERMISSION = "roleIdSetOfpermission";
}
