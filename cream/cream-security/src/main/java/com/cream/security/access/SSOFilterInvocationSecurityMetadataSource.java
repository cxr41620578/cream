/**
 * 
 */
package com.cream.security.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.cream.security.service.ISysPermissionService;

/**
 * @author cream
 *
 */
public class SSOFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    private ISysPermissionService sysPermissionService;

    public SSOFilterInvocationSecurityMetadataSource(ISysPermissionService sysPermissionService,
            FilterInvocationSecurityMetadataSource expressionBasedFilterInvocationSecurityMetadataSource) {
        this.sysPermissionService = sysPermissionService;
        this.filterInvocationSecurityMetadataSource = expressionBasedFilterInvocationSecurityMetadataSource;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>(); 
        configAttributes.addAll(filterInvocationSecurityMetadataSource.getAttributes(object));
        FilterInvocation fi = (FilterInvocation) object;
        Set<Long> roleIds = sysPermissionService.findByUrlAndHttpMethod(fi.getRequestUrl(),
                fi.getRequest().getMethod());
        roleIds.stream().forEach(id -> {
            configAttributes.add(new SecurityConfig("ROLE_" + id));
        });
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public ISysPermissionService getSysPermissionService() {
        return sysPermissionService;
    }

    public void setSysPermissionService(ISysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }
}
