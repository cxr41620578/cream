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
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.cream.core.base.ServerResponse;
import com.cream.core.utils.HttpUtils;

/**
 * 登出成功处理器
 * 
 * @author cream
 *
 */
public class SSOLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (HttpUtils.isAjaxRequest(request)) {
            HttpUtils.writeJson(response,
                    OBJECT_MAPPER.getObjectMapper().writeValueAsString(ServerResponse.error("登出成功")));
        } else {
            super.onLogoutSuccess(request, response, authentication);
        }
    }
}
