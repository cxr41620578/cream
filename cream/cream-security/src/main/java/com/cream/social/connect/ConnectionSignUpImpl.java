/**
 * 
 */
package com.cream.social.connect;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

import com.cream.security.entity.SysUser;
import com.cream.security.service.ISysUserService;

/**
 * @author cream
 *
 */
@Component
public class ConnectionSignUpImpl implements ConnectionSignUp {

    @Autowired
    private ISysUserService sysUserService;
    
    @Override
    public String execute(Connection<?> connection) {
        SysUser sysUser = new SysUser();
        sysUser.setNickname(connection.getDisplayName());
        sysUser.setCreateBy(0L);
        sysUser.setCreateDate(new Date());
        sysUserService.register(sysUser);
        return sysUser.getId().toString();
    }
}