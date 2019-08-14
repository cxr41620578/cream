/**
 * 
 */
package com.cream.social.oauth2;

import java.util.List;
import java.util.Map;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author cream
 *
 */
public class QQOAuth2Template extends OAuth2Template {
    
    private final String openidUrl;
    
    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl, String openidUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        this.openidUrl = openidUrl;
        setUseParametersForClientAuthentication(true);
    }
    
    @SuppressWarnings("unchecked")
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        Map<String, Object> result = getRestTemplate().postForObject(accessTokenUrl, parameters, Map.class);
        if (result == null) {
            throw new RestClientException("access token endpoint returned empty result");
        }
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("access_token", (String) result.get("access_token"));
        QQOpenid qqOpenid = getRestTemplate().postForObject(openidUrl, params, QQOpenid.class);
        result.put("openid", qqOpenid.getOpenid());
        return extractAccessGrant(result);
    }
    
    @Override
    protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn,
            Map<String, Object> response) {
        return new QQAccessGrant(accessToken, scope, refreshToken, expiresIn, (String) response.get("openid"));
    }

    private AccessGrant extractAccessGrant(Map<String, Object> result) {
        return createAccessGrant((String) result.get("access_token"), (String) result.get("scope"), (String) result.get("refresh_token"), getIntegerValue(result, "expires_in"), result);
    }
    
    
    private Long getIntegerValue(Map<String, Object> map, String key) {
        try {
            return Long.valueOf(String.valueOf(map.get(key)));            
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.setErrorHandler(new QQOAuth2ErrorHandler());
        restTemplate.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(restTemplate.getRequestFactory()));
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        converters.add(new QQOAuth2MessageConverter());
        converters.add(new QQOpenidMessageConverter());
        return restTemplate;
    }
}