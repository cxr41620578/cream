/**
 * 
 */
package com.cream.social.api;

import org.springframework.social.ApiBinding;

/**
 * @author cream
 *
 */
public interface QQ extends GraphApi, ApiBinding {

    UserOperations userOperations();
}
