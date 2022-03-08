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
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.social.security.SpringSocialConfigurer;

import com.cream.security.access.SSOFilterInvocationSecurityMetadataSource;
import com.cream.security.authentication.captcha.SpringSecurityCaptchaConfigurer;
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
    private SecurityConfig securityConfig;

    @Autowired
    private ISysPermissionService sysPermissionService;

    @Autowired
    private ISysUserService sysUserService;

    @Resource
    private FindByIndexNameSessionRepository<Session> sessionRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // spring session remember-me
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setAlwaysRemember(true);
        // 权限不足处理器
        AccessDeniedHandler accessDeniedHandler = new SSOUrlAuthenticationEntryPointAccessDeniedHandlerImpl();
        // 登录成功处理器
        SSOLoginSuccessHandler authenticationSuccessHandler = new SSOLoginSuccessHandler();
        authenticationSuccessHandler.setDefaultTargetUrl(securityConfig.getLoginSuccessUrl());
        // 登录失败处理器
        SSOLoginFailureHandler authenticationFailureHandler = new SSOLoginFailureHandler();
        authenticationFailureHandler.setDefaultFailureUrl(securityConfig.getLoginErrorUrl());
        authenticationFailureHandler.setUseForward(true);
        authenticationFailureHandler.setAllowSessionCreation(false);
        // 未登录指向器，转发或重定向登录页面
        AuthenticationEntryPoint authenticationEntryPoint = new SSOUrlAuthenticationEntryPoint(
                securityConfig.getLoginUrl());
        // 登出成功处理器
        LogoutSuccessHandler logoutSuccessHandler = new SSOLogoutSuccessHandler();
        // session过期策略
        SessionInformationExpiredStrategy sessionInformationExpiredStrategy = new SSOSessionInformationExpiredStrategy(
                securityConfig.getLoginInformationExpiredUrl());
        // session无效处理策略
        SSOInvalidSessionStrategy invalidSessionStrategy = new SSOInvalidSessionStrategy(
                securityConfig.getLoginInvalidUrl());
        invalidSessionStrategy.setCreateNewSession(false);
        // 集成spring session
        SpringSessionBackedSessionRegistry<Session> sessionRegistry = new SpringSessionBackedSessionRegistry<Session>(
                this.sessionRepository);
        // 定义权限决策管理器 -新增role投票器 -默认为一票通过，这里改为全票通过
        List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(new WebExpressionVoter(),
                new RoleVoter());
        AccessDecisionManager accessDecisionManager = new UnanimousBased(decisionVoters);
        // 不自动创建session
//        HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
//        httpSessionRequestCache.setCreateSessionAllowed(false);
        
        // session配置
        http.csrf().disable().sessionManagement().invalidSessionStrategy(invalidSessionStrategy)
        .sessionAuthenticationStrategy(new CompositeSessionAuthenticationStrategy(
                Arrays.asList(new SessionFixationProtectionStrategy())))
        .sessionFixation().migrateSession().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .maximumSessions(securityConfig.getMaximumSessions())// 达到最大数禁止登录（预防并发登录
        .maxSessionsPreventsLogin(securityConfig.isMaxSessionsPreventsLogin())
        .expiredSessionStrategy(sessionInformationExpiredStrategy)
        .sessionRegistry(sessionRegistry);
        
        // 权限配置
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
        .authenticationEntryPoint(authenticationEntryPoint);
        
        // url访问管理
        http.authorizeRequests()
        .antMatchers(HttpMethod.GET, securityConfig.getLoginUrl(), // securityConfig.getLoginSuccessUrl(),
                securityConfig.getLoginErrorUrl(), securityConfig.getLogoutUrl(),
                securityConfig.getLogoutSuccessUrl(), "/", "/index", "/error")
        .permitAll()
        .accessDecisionManager(accessDecisionManager)
        .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setSecurityMetadataSource(new SSOFilterInvocationSecurityMetadataSource(
                        sysPermissionService, object.getSecurityMetadataSource()));
                return object;
            }
        })
        .anyRequest().authenticated();
        
        // login logout remember me 配置
        http.formLogin()
        .successHandler(authenticationSuccessHandler)
        .failureHandler(authenticationFailureHandler)
        .loginPage(securityConfig.getLoginUrl())
        .and()
        .logout().logoutSuccessHandler(logoutSuccessHandler).logoutUrl(securityConfig.getLogoutUrl())
        .logoutSuccessUrl(securityConfig.getLogoutSuccessUrl()).permitAll()
        .and()
        .rememberMe()
        .rememberMeServices(rememberMeServices);
        
        // 额外配置
        http.apply(new SpringSocialConfigurer())
        .and()
        .apply(new SpringSecurityCaptchaConfigurer());

        /* http.csrf().disable().sessionManagement().invalidSessionStrategy(invalidSessionStrategy)
                .sessionAuthenticationStrategy(new CompositeSessionAuthenticationStrategy(
                        Arrays.asList(new SessionFixationProtectionStrategy())))
                .sessionFixation().migrateSession().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .maximumSessions(securityConfig.getMaximumSessions())// 达到最大数禁止登录（预防并发登录
                .maxSessionsPreventsLogin(securityConfig.isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy).sessionRegistry(sessionRegistry).and().and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint).and().authorizeRequests()
                // 允许对于网站静态资源的无授权访问
                // .antMatchers(
                // securityConfig.getAnonMatchers().toArray(new
                // String[securityConfig.getAnonMatchers().size()])
                // ).permitAll()
                .antMatchers(HttpMethod.GET, securityConfig.getLoginUrl(), // securityConfig.getLoginSuccessUrl(),
                        securityConfig.getLoginErrorUrl(), securityConfig.getLogoutUrl(),
                        securityConfig.getLogoutSuccessUrl(), "/", "/index", "/error")
                // ignoring
                .permitAll() // 对于获取token的rest api要允许匿名访问
                .accessDecisionManager(accessDecisionManager)
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(new SSOFilterInvocationSecurityMetadataSource(
                                sysPermissionService, object.getSecurityMetadataSource()));
                        return object;
                    }
                })
                .anyRequest().authenticated().and() // 除上面外的所有请求全部需要鉴权认证
//                .requestCache()
//                .requestCache(this.getHttpSessionRequestCache())
//                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .loginPage(securityConfig.getLoginUrl())
                .and()
                .logout().logoutSuccessHandler(logoutSuccessHandler).logoutUrl(securityConfig.getLogoutUrl())
                .logoutSuccessUrl(securityConfig.getLogoutSuccessUrl()).permitAll()
                .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices)
                .and()
                .apply(new SpringSocialConfigurer())
                .and()
                .apply(new SpringSecurityCaptchaConfigurer()); */
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 密码加密算法
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加载用户service
        UserDetailsService userDetailsService = new SSOUserDetailsService(sysUserService);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
                .withObjectPostProcessor(new ObjectPostProcessor<DaoAuthenticationProvider>() {
                    @Override
                    public <O extends DaoAuthenticationProvider> O postProcess(O object) {
                        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
                        reloadableResourceBundleMessageSource.setBasename("classpath:/security/messages");
                        object.setMessageSource(reloadableResourceBundleMessageSource);
                        return object;
                    }
                });
    }
}
