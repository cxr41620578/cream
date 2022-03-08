/**
 * 
 */
package com.cream.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author c-chenxr
 *
 */
public class SecurityUserUtils {

    public static Authentication getCurrentUserAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Object getCurrentPrincipal() {
        return getCurrentUserAuthentication().getPrincipal();
    }
}
