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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.cream.core.base.ServerResponse;
import com.cream.core.utils.HttpUtils;

/**
 * 登录失败处理器
 * 
 * @author cream
 *
 */
public class SSOLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        if (HttpUtils.isAjaxRequest(request)) {
            HttpUtils.writeJson(response,
                    OBJECT_MAPPER.getObjectMapper().writeValueAsString(ServerResponse.error(exception.getMessage())));
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
