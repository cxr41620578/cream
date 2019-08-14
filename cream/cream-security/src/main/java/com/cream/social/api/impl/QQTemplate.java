/**
 * 
 */
package com.cream.social.api.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cream.social.api.QQ;
import com.cream.social.api.UserOperations;

/**
 * @author cream
 *
 */
public class QQTemplate extends AbstractOAuth2ApiBinding implements QQ {
    
    public QQTemplate(String accessToken, String openid, String oauthConsumerKey) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        Assert.hasLength(openid, "openid must not be empty");
        Assert.hasLength(oauthConsumerKey, "oauthConsumerKey must not be empty");
        List<ClientHttpRequestInterceptor> interceptors = getRestTemplate().getInterceptors();
        interceptors.add(new QQParameterRequestInterceptor(openid, oauthConsumerKey));
        initialize();
    }
    
    @Override
    protected MappingJackson2HttpMessageConverter getJsonMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>(mappingJackson2HttpMessageConverter.getSupportedMediaTypes());
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        return mappingJackson2HttpMessageConverter;
    }
    
    @Override
    protected void configureRestTemplate(RestTemplate restTemplate) {
        super.configureRestTemplate(restTemplate);
        restTemplate.setErrorHandler(new QQErrorHandler());
    }

    private UserOperations userOperations;
    
    @Override
    public UserOperations userOperations() {
        return this.userOperations;
    }

    private void initSubApis() {
        this.userOperations = new QQUserTemplate(this);
    }
    
 // private helpers
    private void initialize() {
        // Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
        super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
        initSubApis();
    }

    /*
     * Graph API operations
     */

    @Override
    public <T> T fetchObject(String connectionName, Class<T> type) {
        String connectionPath = connectionName != null && connectionName.length() > 0 ? connectionName : "";
        URI uri = URIBuilder.fromUri(getBaseGraphApiUrl() + connectionPath).build();
        return getRestTemplate().getForObject(uri, type);
    }

    @Override
    public <T> T fetchObject(String connectionName, Class<T> type, String... fields) {
        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();
        if (fields.length > 0) {
            String joinedFields = join(fields);
            queryParameters.set("fields", joinedFields);
        }
        return fetchObject(connectionName, type, queryParameters);
    }

    @Override
    public <T> T fetchObject(String connectionName, Class<T> type, MultiValueMap<String, String> queryParameters) {
        URI uri = URIBuilder.fromUri(getBaseGraphApiUrl() + connectionName).queryParams(queryParameters)
                .build();
        return getRestTemplate().getForObject(uri, type);
    }

    @Override
    public String getBaseGraphApiUrl() {
        return "https://graph.qq.com/";
    }

    private String join(String[] strings) {
        StringBuilder builder = new StringBuilder();
        if (strings.length > 0) {
            builder.append(strings[0]);
            for (int i = 1; i < strings.length; i++) {
                builder.append("," + strings[i]);
            }
        }
        return builder.toString();
    }
}
