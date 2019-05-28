/**
 * 
 */
package com.cream.security.web.session;

import static com.cream.core.singleton.JacksonSingletonEnum.OBJECT_MAPPER;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import com.cream.core.base.ServerResponse;
import com.cream.core.utils.HttpUtils;

/**
 * session失效策略
 * 
 * @author cream
 *
 */
public class SSOSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    private final Log logger = LogFactory.getLog(getClass());
    private final String destinationUrl;
    private final RedirectStrategy redirectStrategy;

    public SSOSessionInformationExpiredStrategy(String invalidSessionUrl) {
        this(invalidSessionUrl, new DefaultRedirectStrategy());
    }

    public SSOSessionInformationExpiredStrategy(String invalidSessionUrl, RedirectStrategy redirectStrategy) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
        this.redirectStrategy = redirectStrategy;
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        if (HttpUtils.isAjaxRequest(event.getRequest())) {
            HttpUtils.writeJson(event.getResponse(),
                    OBJECT_MAPPER.getObjectMapper().writeValueAsString(ServerResponse.error("登录失效")));
        } else {
            logger.debug("Redirecting to '" + destinationUrl + "'");
            redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), destinationUrl);
        }
    }

}
