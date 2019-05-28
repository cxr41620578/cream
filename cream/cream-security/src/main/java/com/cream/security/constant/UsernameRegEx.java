/**
 * 
 */
package com.cream.security.constant;

/**
 * 用户名正则
 * 
 * @author cream
 *
 */
public interface UsernameRegEx {

    String USERNAME = "^[a-zA-Z0-9_-]{4,16}$";
    String PHONE = "^1(3|4|5|7|8)\\d{9}$";
    String EMAIL = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
}
