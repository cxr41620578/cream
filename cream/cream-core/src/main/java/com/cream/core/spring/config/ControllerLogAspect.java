/**
 * 
 */
package com.cream.core.spring.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cream.core.utils.HttpUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cream
 *
 */
@Aspect
@Component
@Order(0)
@Slf4j
public class ControllerLogAspect {

    @Pointcut("execution(public * com.cream..controller.*Controller.*(..))")
    public void controllerLog() {
    }
    
    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuilder sb = new StringBuilder("\n url : " + request.getRequestURL().toString())
        .append("\n httpMethod: " + request.getMethod())
        .append("\n ip: " + HttpUtils.getIpAddress(request))
        .append("\n classMethod: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName())
        .append("\n args: " + Arrays.toString(joinPoint.getArgs()));
        log.info(sb.toString());
    }
    
    @AfterReturning(returning = "ret", pointcut = "controllerLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
    }
}
