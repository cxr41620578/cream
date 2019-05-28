/**
 * 
 */
package com.cream.social.api;

import java.io.Serializable;

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
public class QQObject implements Serializable {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    /**
     * 返回码
     */
    private String ret;
    
    /**
     * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码
     */
    private String msg;
    
}
