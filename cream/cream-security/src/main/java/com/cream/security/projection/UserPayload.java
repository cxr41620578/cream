/**
 * 
 */
package com.cream.security.projection;

import org.springframework.data.web.ProjectedPayload;

/**
 * @author cream
 *
 */
@ProjectedPayload
public interface UserPayload {
    
    long getId();

    String getUserPassword();
}
