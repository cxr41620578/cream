/**
 * 
 */
package com.cream.security.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;

import com.cream.core.CreamVersion;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cream
 *
 */
@Getter
@AllArgsConstructor
public class SSOUserDetails implements UserDetails, SocialUserDetails {
    
    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    /**
     * 权限
     */
    private Collection<GrantedAuthority> authorities;

    /**
     * 用户主键
     */
    private long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 是否开启验证码验证
     */
    private boolean isTurnOnCaptcha;

    /**
     * 账户是否过期,过期无法验证
     */
    private boolean isAccountNonExpired;

    /**
     * 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
     */
    private boolean isAccountNonLocked;

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    private boolean isCredentialsNonExpired;

    /**
     * 是否被禁用,禁用的用户不能身份验证
     */
    private boolean isEnabled;

    @Override
    public String getUserId() {
        return String.valueOf(this.id);
    }
}