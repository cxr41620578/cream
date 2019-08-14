/**
 * 
 */
package com.cream.social.entity;

import java.io.Serializable;

import com.cream.core.CreamVersion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author v-chenxr04
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserConnectionKey implements Serializable {
    
    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 服务提供商
     */
    private String providerId;
    
    /**
     * 服务提供商返回的用户唯一标识
     */
    private String providerUserId;
}
