/**
 * 
 */
package com.cream.security.handler;

import static com.cream.core.singleton.JacksonSingletonEnum.OBJECT_MAPPER;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.cream.core.base.ServerResponse;
import com.cream.core.utils.HttpUtils;

/**
 * 未登录指向器，转发或重定向登录页面
 * 
 * @author cream
 * @date 2017-11-01
 *
 */
public class SSOUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public SSOUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        System.out.println(request.getSession(false));
        if (HttpUtils.isAjaxRequest(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            HttpUtils.writeJson(response,
                    OBJECT_MAPPER.getObjectMapper().writeValueAsString(ServerResponse.error("访问此资源需要完全身份验证")));
        } else {
            super.commence(request, response, authException);
        }
        System.out.println(request.getSession(false));
    }
}
