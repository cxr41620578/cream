/**
 * 
 */
package com.cream.security.web.session;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import com.cream.core.base.ServerResponse;
import com.cream.core.singleton.JacksonSingletonEnum;
import com.cream.core.utils.HttpUtils;

/**
 * session超时
 * 
 * @author cream
 *
 */
public class SSOInvalidSessionStrategy implements InvalidSessionStrategy {

    private final Log logger = LogFactory.getLog(getClass());
    private final String destinationUrl;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private boolean createNewSession = true;

    public SSOInvalidSessionStrategy(String invalidSessionUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("Starting new session (if required) and redirecting to '" + destinationUrl + "'");
        if (createNewSession) {
            request.getSession();
        }
        if (HttpUtils.isAjaxRequest(request)) {
            HttpUtils.writeJson(response, JacksonSingletonEnum.OBJECT_MAPPER.getObjectMapper()
                    .writeValueAsString(ServerResponse.error("登录超时")));
        } else {
            redirectStrategy.sendRedirect(request, response, destinationUrl);
        }
    }

    /**
     * Determines whether a new session should be created before redirecting (to
     * avoid possible looping issues where the same session ID is sent with the
     * redirected request). Alternatively, ensure that the configured URL does not
     * pass through the {@code SessionManagementFilter}.
     *
     * @param createNewSession
     *            defaults to {@code true}.
     */
    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}
