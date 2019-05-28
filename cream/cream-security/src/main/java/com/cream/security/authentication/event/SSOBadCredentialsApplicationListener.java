/**
 * 
 */
package com.cream.security.authentication.event;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * @author cream
 *
 */
@Component
public class SSOBadCredentialsApplicationListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

//    @Autowired
//    private ISysUserAttemptsService sysUserAttemptsService;
    
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
//        String username = event.getAuthentication().getPrincipal().toString();
//        sysUserAttemptsService.accAttempts(username);
    }
}
