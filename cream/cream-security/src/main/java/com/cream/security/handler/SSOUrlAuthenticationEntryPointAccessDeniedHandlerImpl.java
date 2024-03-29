/**
 * 
 */
package com.cream.security.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.cream.core.base.ServerResponse;
import com.cream.core.singleton.JacksonSingletonEnum;
import com.cream.core.utils.HttpUtils;

/**
 * 拒绝访问处理器
 * 
 * @author cream
 *
 */
public class SSOUrlAuthenticationEntryPointAccessDeniedHandlerImpl implements AccessDeniedHandler {

    private String errorPage;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (HttpUtils.isAjaxRequest(request)) {
            // Set the 403 status code.
            response.setStatus(HttpStatus.FORBIDDEN.value());
            HttpUtils.writeJson(response, JacksonSingletonEnum.OBJECT_MAPPER.getObjectMapper()
                    .writeValueAsString(ServerResponse.error(accessDeniedException.getMessage())));
        } else if (!response.isCommitted()) {
            if (errorPage != null) {
                // Put exception into request scope (perhaps of use to a view)
                request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);

                // Set the 403 status code.
                response.setStatus(HttpStatus.FORBIDDEN.value());

                // forward to error page.
                RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
                dispatcher.forward(request, response);
            } else {
                response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
            }
        }
    }

    /**
     * The error page to use. Must begin with a "/" and is interpreted relative to
     * the current context root.
     *
     * @param errorPage
     *            the dispatcher path to display
     *
     * @throws IllegalArgumentException
     *             if the argument doesn't comply with the above limitations
     */
    public void setErrorPage(String errorPage) {
        if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }

        this.errorPage = errorPage;
    }
}
