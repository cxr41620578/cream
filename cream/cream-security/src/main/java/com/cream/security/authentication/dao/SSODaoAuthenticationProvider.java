/**
 * 
 */
package com.cream.security.authentication.dao;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.cream.security.authentication.SSOAuthenticationToken;
import com.cream.security.constant.CacheConstant;
import com.cream.security.exception.BadCaptchaException;
import com.cream.security.userdetails.SSOUserDetails;

/**
 * @author cream
 *
 */
//public class SSODaoAuthenticationProvider extends DaoAuthenticationProvider {
//
//    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
//
//    private StringRedisTemplate stringRedisTemplate;
//
//    public SSODaoAuthenticationProvider() {
//        super();
//    }
//
//    @Override
//    protected void doAfterPropertiesSet() throws Exception {
//        super.doAfterPropertiesSet();
//    }
//
//    @Override
//    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
//            UserDetails user) {
//        Assert.isInstanceOf(SSOAuthenticationToken.class, authentication, "Only SSOAuthenticationToken is supported");
//        SSOAuthenticationToken ssoAuthenticationToken = (SSOAuthenticationToken) authentication;
//
//        SSOAuthenticationToken result = new SSOAuthenticationToken(principal, authentication.getCredentials(),
//                ssoAuthenticationToken.getCaptcha(), authoritiesMapper.mapAuthorities(user.getAuthorities()));
//        result.setDetails(authentication.getDetails());
//        return result;
//    }
//
//    protected void additionalAuthenticationChecks(UserDetails userDetails,
//            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        super.additionalAuthenticationChecks(userDetails, authentication);
//        Assert.isInstanceOf(SSOAuthenticationToken.class, authentication, "Only SSOAuthenticationToken is supported");
//        SSOAuthenticationToken ssoAuthenticationToken = (SSOAuthenticationToken) authentication;
//        Assert.isInstanceOf(SSOUserDetails.class, userDetails, "Only SSOUserDetails is supported");
//        SSOUserDetails ssoUserDetails = (SSOUserDetails) userDetails;
//
//        if (ssoUserDetails.isTurnOnCaptcha()) {
//            String captchaKey = CacheConstant.USER_CAPTCHA + ssoUserDetails.getId();
//            String captchaExpiresKey = CacheConstant.USER_CAPTCHA + ssoUserDetails.getId();
//            Object captcha = stringRedisTemplate.opsForHash().get(CacheConstant.BASE_CAPTCHA, captchaKey);
//            if (null != captcha && ((String) captcha).equals(ssoAuthenticationToken.getCaptcha())) {
//                Object captchaExpires = stringRedisTemplate.opsForHash().get(CacheConstant.BASE_CAPTCHA, captchaExpiresKey);
//                if (null != captchaExpires) {
//                    if ((long) captchaExpires > System.currentTimeMillis()) {
//                        throw new BadCaptchaException("验证码错误");
//                    }
//                }
//            } else {
//                throw new BadCaptchaException("验证码错误");
//            }
//        }
//    }
//
//    public StringRedisTemplate getStringRedisTemplate() {
//        return stringRedisTemplate;
//    }
//
//    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
//        this.stringRedisTemplate = stringRedisTemplate;
//    }
//}
