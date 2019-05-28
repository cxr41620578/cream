/**
 * 
 */
package com.cream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.social.facebook.config.support.FacebookApiHelper;

import com.cream.core.base.repository.BaseRepositoryFactoryBean;

/**
 * @author cream
 *
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class Bootstrap implements CommandLineRunner {

    public static void main(String[] args) {
//        AbstractSecurityInterceptor;
//        AccessDecisionManager;
//        WebExpressionConfigAuthenticate
//        FilterSecurityInterceptor;
//        AccessDecisionManager;
//        DefaultWebSecurityExpressionHandler
//        RequestHeaderAuthenticationFilter
//        SimpleKeyGenerator
//        AbstractOAuth2ApiBinding
//        ConnectController;
//        ProviderSignInController
        SpringApplication.run(Bootstrap.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        System.out.println("服务启动完成！");
    }
}
