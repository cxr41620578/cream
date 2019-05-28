/**
 * 
 */
package com.cream.core.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cream.core.base.ServerResponse;
import com.cream.core.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cream
 *
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 400 Bad Request
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ServerResponse<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ServerResponse.error("参数解析失败");
    }
    
    /**
     * 405 Method Not Allowed
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ServerResponse<?> handleHttpRequestMethodNotSupportedException(HttpMessageNotReadableException e) {
        return ServerResponse.error("不支持当前请求方法");
    }
    
    /**
     * 415 Unsupported Media Type
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ServerResponse<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return ServerResponse.error("不支持当前媒体类型");
    }
    
    /**
     * 500 Bad Request
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ServerResponse<?> handleException(Exception e) {
        e.printStackTrace();
        if (e instanceof BusinessException){
            BusinessException businessException = (BusinessException) e;
            return ServerResponse.error(businessException.getCode(), e.getMessage());
        } else {
            return ServerResponse.error("服务器内部错误");
        }
    }
}
