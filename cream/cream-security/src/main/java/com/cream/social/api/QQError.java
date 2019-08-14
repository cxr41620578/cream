/**
 * 
 */
package com.cream.social.api;

/**
 * @author v-chenxr04
 *
 */
public class QQError {

    private final Integer ret;
    
    private final String msg;
    
    public QQError(Integer ret, String msg) {
        this.ret = ret;
        this.msg = msg;
    }

    public Integer getRet() {
        return ret;
    }

    public String getMsg() {
        return msg;
    }
}
