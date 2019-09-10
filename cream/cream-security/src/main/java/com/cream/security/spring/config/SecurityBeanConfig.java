/**
 * 
 */
package com.cream.security.spring.config;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import com.cream.security.handler.SSOLoginFailureHandler;
import com.cream.security.handler.SSOLoginSuccessHandler;
import com.cream.security.handler.SSOLogoutSuccessHandler;
import com.cream.security.handler.SSOUrlAuthenticationEntryPoint;
import com.cream.security.handler.SSOUrlAuthenticationEntryPointAccessDeniedHandlerImpl;
import com.cream.security.service.ISysUserService;
import com.cream.security.userdetails.SSOUserDetailsService;
import com.cream.security.web.session.SSOInvalidSessionStrategy;
import com.cream.security.web.session.SSOSessionInformationExpiredStrategy;

/**
 * @author cream
 *
 */
@Configuration
public class SecurityBeanConfig {

//    @Autowired
//    private SecurityConfig securityConfig;
//
//    @Autowired
//    private ISysUserService sysUserService;
//
//    @Resource
//    private FindByIndexNameSessionRepository<Session> sessionRepository;
//
//    /**
//     * spring session remember-me
//     * 
//     * @return
//     */
//    @Bean
//    public RememberMeServices rememberMeServices() {
//        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
//        rememberMeServices.setAlwaysRemember(true);
//        return rememberMeServices;
//    }
//    
//    /**
//     * 权限不足处理器
//     * 
//     * @return
//     */
//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        SSOUrlAuthenticationEntryPointAccessDeniedHandlerImpl ssoUrlAuthenticationEntryPointAccessDeniedHandlerImpl = new SSOUrlAuthenticationEntryPointAccessDeniedHandlerImpl();
//        return ssoUrlAuthenticationEntryPointAccessDeniedHandlerImpl;
//    }
//
//    /**
//     * 登录成功处理器
//     * 
//     * @return
//     */
//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        SSOLoginSuccessHandler ssoLoginSuccessHandler = new SSOLoginSuccessHandler();
//        ssoLoginSuccessHandler.setDefaultTargetUrl(securityConfig.getLoginSuccessUrl());
//        return ssoLoginSuccessHandler;
//    }
//
//    /**
//     * 登录失败处理器
//     * 
//     * @return
//     */
//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        SSOLoginFailureHandler ssoLoginFailureHandler = new SSOLoginFailureHandler();
//        ssoLoginFailureHandler.setDefaultFailureUrl(securityConfig.getLoginErrorUrl());
//        return ssoLoginFailureHandler;
//    }
//
//    /**
//     * 未登录指向器，转发或重定向登录页面
//     * 
//     * @return
//     */
//    @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint() {
//        SSOUrlAuthenticationEntryPoint ssoUrlAuthenticationEntryPoint = new SSOUrlAuthenticationEntryPoint(
//                securityConfig.getLoginUrl());
//        return ssoUrlAuthenticationEntryPoint;
//    }
//
//    /**
//     * 登出处理器
//     * 
//     * @return
//     */
//    @Bean
//    public LogoutHandler logoutHandler() {
//        SpringSessionRememberMeServices springSessionRememberMeServices = new SpringSessionRememberMeServices();
//        return springSessionRememberMeServices;
//    }
//
//    /**
//     * 登出成功处理器
//     * 
//     * @return
//     */
//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() {
//        SSOLogoutSuccessHandler ssoLogoutSuccessHandler = new SSOLogoutSuccessHandler();
//        return ssoLogoutSuccessHandler;
//    }
//
//    /**
//     * 密码加密算法
//     * 
//     * @return
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * 加载用户service
//     * 
//     * @return
//     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        SSOUserDetailsService ssoUserDetailsService = new SSOUserDetailsService(sysUserService);
//        return ssoUserDetailsService;
//    }
//
//    /**
//     * session过期策略
//     * 
//     * @return
//     */
//    @Bean
//    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
//        SSOSessionInformationExpiredStrategy ssoSessionInformationExpiredStrategy = new SSOSessionInformationExpiredStrategy(
//                securityConfig.getLoginInformationExpiredUrl());
//        return ssoSessionInformationExpiredStrategy;
//    }
//
//    /**
//     * session无效处理策略
//     * 
//     * @return
//     */
//    @Bean
//    public InvalidSessionStrategy invalidSessionStrategy() {
//        SSOInvalidSessionStrategy ssoInvalidSessionStrategy = new SSOInvalidSessionStrategy(
//                securityConfig.getLoginInvalidUrl());
//        ssoInvalidSessionStrategy.setCreateNewSession(false);
//        return ssoInvalidSessionStrategy;
//    }
//
//    /**
//     * 集成spring session
//     * 
//     * @return
//     */
//    @Bean
//    public SpringSessionBackedSessionRegistry<Session> sessionRegistry() {
//        return new SpringSessionBackedSessionRegistry<Session>(this.sessionRepository);
//    }
//
//    /**
//     * 国际化
//     * 
//     * @return
//     */
//    @Bean
//    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
//        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
//        reloadableResourceBundleMessageSource.setBasename("classpath:/security/messages");
//        return reloadableResourceBundleMessageSource;
//    }
//
//
//    /**
//     * 定义权限决策管理器 -新增role投票器 -默认为一票通过，这里改为全票通过
//     * 
//     * @return
//     */
//    @Bean
//    public AccessDecisionManager accessDecisionManager() {
//        List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(new WebExpressionVoter(),
//                new RoleVoter());
//        return new UnanimousBased(decisionVoters);
//    }
    
//    public HttpSessionRequestCache getHttpSessionRequestCache() {
//        HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
//        // 不自动创建session
//        httpSessionRequestCache.setCreateSessionAllowed(false);
//        return httpSessionRequestCache;
//    }
}
