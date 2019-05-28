/**
 * 
 */
package com.cream.social.api;

import com.cream.core.CreamVersion;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author cream
 *
 */
@Getter
@Setter
@Accessors(chain = true)
public class QQUser extends QQObject {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    private String openId;
    
    private String nickname;
    
    private String figureurl;
    
    private String figureurl_1;
    
    private String figureurl_2;
    
    private String figureurl_qq_1;
    
    private String figureurl_qq_2;
    
    private String gender;
    
    private String is_yellow_vip;
    
    private String vip;
    
    private String yellow_vip_level;
    
    private String level;
    
    private String is_yellow_year_vip;
}
