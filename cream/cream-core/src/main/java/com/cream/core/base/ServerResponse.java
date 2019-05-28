/**
 * 
 */
package com.cream.core.base;

import java.io.Serializable;

import com.cream.core.CreamVersion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;

/**
 * @author cream
 *
 */
@Getter
@JsonSerialize
public class ServerResponse<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    private ResponseStatusEnum status;

    private String msg;

    private T data;

    private ServerResponse(ResponseStatusEnum status) {
        this.status = status;
    }

    private ServerResponse(ResponseStatusEnum status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(ResponseStatusEnum status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(ResponseStatusEnum status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return ResponseStatusEnum.SUCCESS.equals(status);
    }

    public static <T> ServerResponse<T> success() {
        return new ServerResponse<T>(ResponseStatusEnum.SUCCESS);
    }

    public static <T> ServerResponse<T> success(String msg) {
        return new ServerResponse<T>(ResponseStatusEnum.SUCCESS, msg);
    }

    public static <T> ServerResponse<T> success(T data) {
        return new ServerResponse<T>(ResponseStatusEnum.SUCCESS, data);
    }

    public static <T> ServerResponse<T> success(String msg, T data) {
        return new ServerResponse<T>(ResponseStatusEnum.SUCCESS, msg, data);
    }

    public static <T> ServerResponse<T> error() {
        return new ServerResponse<T>(ResponseStatusEnum.ERROR);
    }

    public static <T> ServerResponse<T> error(String msg) {
        return new ServerResponse<T>(ResponseStatusEnum.ERROR, msg);
    }

    public static <T> ServerResponse<T> error(T data) {
        return new ServerResponse<T>(ResponseStatusEnum.ERROR, data);
    }

    public static <T> ServerResponse<T> error(String msg, T data) {
        return new ServerResponse<T>(ResponseStatusEnum.ERROR, msg, data);
    }
}
