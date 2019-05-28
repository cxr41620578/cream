/**
 * 
 */
package com.cream.security.handler;

import static com.cream.core.singleton.JacksonSingletonEnum.OBJECT_MAPPER;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.cream.core.base.ServerResponse;
import com.cream.core.utils.HttpUtils;

/**
 * 登录成功处理器
 * 
 * @author cream
 *
 */
public class SSOLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        if (HttpUtils.isAjaxRequest(request)) {
            HttpUtils.writeJson(response,
                    OBJECT_MAPPER.getObjectMapper().writeValueAsString(ServerResponse.error("登录成功")));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
