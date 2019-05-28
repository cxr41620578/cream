/**
 * 
 */
package com.cream.security.spring.config;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import com.cream.security.access.SSOFilterInvocationSecurityMetadataSource;
import com.cream.security.authentication.SSOAuthenticationFilter;
import com.cream.security.authentication.dao.SSODaoAuthenticationProvider;
import com.cream.security.handler.SSOLoginFailureHandler;
import com.cream.security.handler.SSOLoginSuccessHandler;
import com.cream.security.handler.SSOLogoutSuccessHandler;
import com.cream.security.handler.SSOUrlAuthenticationEntryPoint;
import com.cream.security.handler.SSOUrlAuthenticationEntryPointAccessDeniedHandlerImpl;
import com.cream.security.service.ISysPermissionService;
import com.cream.security.service.ISysUserService;
import com.cream.security.userdetails.SSOUserDetailsService;
import com.cream.security.web.session.SSOInvalidSessionStrategy;
import com.cream.security.web.session.SSOSessionInformationExpiredStrategy;

/**
 * @author cream
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RememberMeServices rememberMeServices;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysPermissionService sysPermissionService;

    @Resource
    private FindByIndexNameSessionRepository<Session> sessionRepository;

    /**
     * 权限不足处理器
     * 
     * @return
     */
    public AccessDeniedHandler accessDeniedHandlerImpl() {
        SSOUrlAuthenticationEntryPointAccessDeniedHandlerImpl ssoUrlAuthenticationEntryPointAccessDeniedHandlerImpl = new SSOUrlAuthenticationEntryPointAccessDeniedHandlerImpl();
        return ssoUrlAuthenticationEntryPointAccessDeniedHandlerImpl;
    }

    /**
     * 登录成功处理器
     * 
     * @return
     */
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        SSOLoginSuccessHandler ssoLoginSuccessHandler = new SSOLoginSuccessHandler();
        ssoLoginSuccessHandler.setDefaultTargetUrl(securityConfig.getLoginSuccessUrl());
        return ssoLoginSuccessHandler;
    }

    /**
     * 登录失败处理器
     * 
     * @return
     */
    public AuthenticationFailureHandler authenticationFailureHandler() {
        SSOLoginFailureHandler ssoLoginFailureHandler = new SSOLoginFailureHandler();
        ssoLoginFailureHandler.setDefaultFailureUrl(securityConfig.getLoginErrorUrl());
        return ssoLoginFailureHandler;
    }

    /**
     * 未登录指向器，转发或重定向登录页面
     * 
     * @return
     */
    public AuthenticationEntryPoint authenticationEntryPoint() {
        SSOUrlAuthenticationEntryPoint ssoUrlAuthenticationEntryPoint = new SSOUrlAuthenticationEntryPoint(
                securityConfig.getLoginUrl());
        return ssoUrlAuthenticationEntryPoint;
    }

    /**
     * 登出处理器
     * 
     * @return
     */
    public LogoutHandler logoutHandler() {
        SpringSessionRememberMeServices springSessionRememberMeServices = new SpringSessionRememberMeServices();
        return springSessionRememberMeServices;
    }

    /**
     * 登出成功处理器
     * 
     * @return
     */
    public LogoutSuccessHandler logoutSuccessHandler() {
        SSOLogoutSuccessHandler ssoLogoutSuccessHandler = new SSOLogoutSuccessHandler();
        return ssoLogoutSuccessHandler;
    }

    /**
     * 密码加密算法
     * 
     * @return
     */
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 加载用户service
     * 
     * @return
     */
    public UserDetailsService userDetailsService() {
        SSOUserDetailsService ssoUserDetailsService = new SSOUserDetailsService(sysUserService);
        return ssoUserDetailsService;
    }

    /**
     * session过期策略
     * 
     * @return
     */
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        SSOSessionInformationExpiredStrategy ssoSessionInformationExpiredStrategy = new SSOSessionInformationExpiredStrategy(
                securityConfig.getLoginInformationExpiredUrl());
        return ssoSessionInformationExpiredStrategy;
    }

    /**
     * session无效处理策略
     * 
     * @return
     */
    public InvalidSessionStrategy invalidSessionStrategy() {
        SSOInvalidSessionStrategy ssoInvalidSessionStrategy = new SSOInvalidSessionStrategy(
                securityConfig.getLoginInvalidUrl());
        ssoInvalidSessionStrategy.setCreateNewSession(false);
        return ssoInvalidSessionStrategy;
    }

    /**
     * 集成spring session
     * 
     * @return
     */
    public SpringSessionBackedSessionRegistry<Session> sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<Session>(this.sessionRepository);
    }

    /**
     * 国际化
     * 
     * @return
     */
    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:/security/messages");
        return reloadableResourceBundleMessageSource;
    }

    /**
     * 自定义登录Provider
     * 
     * @return
     */
    public AuthenticationProvider authenticationProvider() {
        SSODaoAuthenticationProvider ssoDaoAuthenticationProvider = new SSODaoAuthenticationProvider();
        ssoDaoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
        ssoDaoAuthenticationProvider.setUserDetailsService(this.userDetailsService());
        ssoDaoAuthenticationProvider.setMessageSource(this.reloadableResourceBundleMessageSource());
        return ssoDaoAuthenticationProvider;
    }

    /**
     * 定义权限决策管理器 -新增role投票器 -默认为一票通过，这里改为全票通过
     * 
     * @return
     */
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(new WebExpressionVoter(),
                new RoleVoter());
        return new UnanimousBased(decisionVoters);
    }

    public SSOAuthenticationFilter ssoAuthenticationFilter() throws Exception {
        SSOAuthenticationFilter ssoAuthenticationFilter = new SSOAuthenticationFilter(securityConfig.getLoginUrl(),
                HttpMethod.POST.name());
        ssoAuthenticationFilter.setAuthenticationManager(this.authenticationManagerBean());
        ssoAuthenticationFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler());
        ssoAuthenticationFilter.setAuthenticationFailureHandler(this.authenticationFailureHandler());
        ssoAuthenticationFilter.setRememberMeServices(rememberMeServices);
        return ssoAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().invalidSessionStrategy(this.invalidSessionStrategy())
                .sessionCreationPolicy(SessionCreationPolicy.NEVER).maximumSessions(securityConfig.getMaximumSessions())// 达到最大数禁止登录（预防并发登录
                .maxSessionsPreventsLogin(securityConfig.isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(this.sessionInformationExpiredStrategy())
                .sessionRegistry(this.sessionRegistry()).and().and().exceptionHandling()
                .accessDeniedHandler(this.accessDeniedHandlerImpl())
                .authenticationEntryPoint(this.authenticationEntryPoint()).and().authorizeRequests()
                .permitAll() // 对于获取token的rest api要允许匿名访问
                .accessDecisionManager(accessDecisionManager())
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(new SSOFilterInvocationSecurityMetadataSource(
                                sysPermissionService, object.getSecurityMetadataSource()));
                        return object;
                    }
                }).anyRequest().authenticated().and() // 除上面外的所有请求全部需要鉴权认证
                .logout().addLogoutHandler(this.logoutHandler()).logoutSuccessHandler(this.logoutSuccessHandler())
                .logoutUrl(securityConfig.getLogoutUrl()).logoutSuccessUrl(securityConfig.getLogoutSuccessUrl())
                .permitAll().and().rememberMe().rememberMeServices(rememberMeServices).and()
                .addFilterAt(this.ssoAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationProvider());
    }
}
