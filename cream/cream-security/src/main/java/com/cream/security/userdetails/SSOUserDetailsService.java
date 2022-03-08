/**
 * 
 */
package com.cream.security.userdetails;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cream.security.projection.UserPayload;
import com.cream.security.service.ISysUserService;

/**
 * @author cream
 *
 */
@Service
public class SSOUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    private ISysUserService sysUserService;
    
    public SSOUserDetailsService(ISysUserService sysUserService) {
        Assert.notNull(sysUserService, "an sysUserService is required");
        this.sysUserService = sysUserService;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        UserPayload userProjection = sysUserService.findByUsername(username);
        if (userProjection == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        Set<Long> roleIds = sysUserService.findRoleIdsByUsername(username);
        Set<GrantedAuthority> grantedAuthoritys = new HashSet<GrantedAuthority>();
        roleIds.forEach(id -> {
            grantedAuthoritys.add(new SimpleGrantedAuthority("ROLE_" + id));
        });
        return new User(String.valueOf(userProjection.getId()), userProjection.getUserPassword(), grantedAuthoritys);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        if (userId == null) {
            throw new UsernameNotFoundException(String.format("No user found with userId '%s'.", userId));
        } else {
            long id = Long.parseLong(userId);
            UserPayload userProjection = sysUserService.getById(id);
            Set<Long> roleIds = sysUserService.findRoleIdsById(id);
            Set<GrantedAuthority> grantedAuthoritys = new HashSet<GrantedAuthority>();
            roleIds.forEach(v -> {
                grantedAuthoritys.add(new SimpleGrantedAuthority("ROLE_" + v));
            });
            return new SocialUser(String.valueOf(userProjection.getId()), userProjection.getUserPassword(), grantedAuthoritys);
        }
    }
}