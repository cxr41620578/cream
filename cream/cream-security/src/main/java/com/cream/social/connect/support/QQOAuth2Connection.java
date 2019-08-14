/**
 * 
 */
package com.cream.social.connect.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.core.GenericTypeResolver;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.ServiceProvider;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.AbstractConnection;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import com.cream.social.api.QQ;
import com.cream.social.oauth2.QQAccessGrant;
import com.cream.social.oauth2.QQOAuth2ServiceProvider;

/**
 * @author v-chenxr04
 *
 */
public class QQOAuth2Connection extends AbstractConnection<QQ> {

    private static final long serialVersionUID = 1L;

    private transient final QQOAuth2ServiceProvider serviceProvider;

    private String accessToken;
    
    private String refreshToken;
    
    private Long expireTime;

    private transient QQ api;
    
    private transient QQ apiProxy;

    /**
     * Creates a new {@link OAuth2Connection} from a access grant response.
     * Designed to be called to establish a new {@link OAuth2Connection} after receiving an access grant successfully.
     * The providerUserId may be null in this case: if so, this constructor will try to resolve it using the service API obtained from the {@link OAuth2ServiceProvider}.
     * @param providerId the provider id e.g. "facebook".
     * @param providerUserId the provider user id (may be null if not returned as part of the access grant)
     * @param accessToken the granted access token
     * @param refreshToken the granted refresh token
     * @param expireTime the access token expiration time
     * @param serviceProvider the OAuth2-based ServiceProvider
     * @param apiAdapter the ApiAdapter for the ServiceProvider
     */
    public QQOAuth2Connection(String providerId, String providerUserId, String accessToken, String refreshToken, Long expireTime,
            QQOAuth2ServiceProvider serviceProvider, ApiAdapter<QQ> apiAdapter) {
        super(apiAdapter);
        this.serviceProvider = serviceProvider;
        initAccessTokens(accessToken, refreshToken, expireTime);
        initApi(providerUserId);
        initApiProxy();
        initKey(providerId, providerUserId);
    }
    
    /**
     * Creates a new {@link OAuth2Connection} from the data provided.
     * Designed to be called when re-constituting an existing {@link Connection} from {@link ConnectionData}.
     * @param data the data holding the state of this connection
     * @param serviceProvider the OAuth2-based ServiceProvider
     * @param apiAdapter the ApiAdapter for the ServiceProvider
     */
    public QQOAuth2Connection(ConnectionData data, QQOAuth2ServiceProvider serviceProvider, ApiAdapter<QQ> apiAdapter) {
        super(data, apiAdapter);
        this.serviceProvider = serviceProvider;
        initAccessTokens(data.getAccessToken(), data.getRefreshToken(), data.getExpireTime());
        initApi(data.getProviderUserId());
        initApiProxy();
    }

    // implementing Connection

    public boolean hasExpired() {
        synchronized (getMonitor()) {
            return expireTime != null && System.currentTimeMillis() >= expireTime;
        }
    }

    public void refresh() {
        synchronized (getMonitor()) {
            AccessGrant accessGrant = serviceProvider.getOAuthOperations().refreshAccess(refreshToken, null);
            initAccessTokens(accessGrant.getAccessToken(), accessGrant.getRefreshToken(), accessGrant.getExpireTime());
            initApi(((QQAccessGrant) accessGrant).getOpenid());
        }
    }

    public QQ getApi() {
        if (apiProxy != null) {
            return apiProxy;
        } else {
            synchronized (getMonitor()) {
                return api;
            }
        }
    }

    public ConnectionData createData() {
        synchronized (getMonitor()) {
            return new ConnectionData(getKey().getProviderId(), getKey().getProviderUserId(), getDisplayName(), getProfileUrl(), getImageUrl(), accessToken, null, refreshToken, expireTime);
        }
    }

    // internal helpers
    
    private void initAccessTokens(String accessToken, String refreshToken, Long expireTime) {
        this.accessToken = accessToken;
        this.expireTime = expireTime;       
        if (refreshToken != null) {
            this.refreshToken = refreshToken;
        }
    }
    
    private void initApi(String providerUserId) {
        api = serviceProvider.getApi(accessToken, providerUserId);
    }
    
    private void initApiProxy() {
        Class<?> apiType = GenericTypeResolver.resolveTypeArgument(serviceProvider.getClass(), ServiceProvider.class);
        if (apiType.isInterface()) {
            apiProxy = (QQ) Proxy.newProxyInstance(apiType.getClassLoader(), new Class<?>[] { apiType }, new ApiInvocationHandler());
        }       
    }
    
    private class ApiInvocationHandler implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            synchronized (getMonitor()) {
                if (hasExpired()) {
                    throw new ExpiredAuthorizationException(getKey().getProviderId());
                }
                try {
                    return method.invoke(QQOAuth2Connection.this.api, args);
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
        }
    }

    // equas() and hashCode() generated by Eclipse
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((accessToken == null) ? 0 : accessToken.hashCode());
        result = prime * result + ((expireTime == null) ? 0 : expireTime.hashCode());
        result = prime * result + ((refreshToken == null) ? 0 : refreshToken.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        QQOAuth2Connection other = (QQOAuth2Connection) obj;
        
        if (accessToken == null) {
            if (other.accessToken != null) return false;
        } else if (!accessToken.equals(other.accessToken)) return false;

        if (expireTime == null) {
            if (other.expireTime != null) return false;
        } else if (!expireTime.equals(other.expireTime)) return false;
        
        if (refreshToken == null) {
            if (other.refreshToken != null) return false;
        } else if (!refreshToken.equals(other.refreshToken)) return false;

        return true;
    }
}
