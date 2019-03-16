package com.dereekb.gae.server.auth.security.login.oauth.impl.service.google;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthClientConfig;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthClientConfigImpl;

/**
 * Abstract {@link OAuthService} implementation.
 *
 * @author dereekb
 *
 */
@Deprecated
public abstract class AbstractOAuthService
        implements OAuthService {

	private static final String DEFAULT_STATE = "/login";
	private static final String DEFAULT_AUTH_TOKEN_REDIRECT_URL = "";

	/**
	 * Path to the access token step. The initial step.
	 */
	private String serverLoginTokenUrl;

	/**
	 * Path to the authorization token step, after retrieving the access token
	 * request.
	 */
	private String serverAuthTokenUrl;

	/**
	 * OAuth State
	 */
	private String state;

	private OAuthClientConfig client;

	private String loginTokenRedirectUrl = null;
	private String authTokenRedirectUrl = DEFAULT_AUTH_TOKEN_REDIRECT_URL;

	private List<String> scopes;

	@Deprecated
	public AbstractOAuthService(String serverLoginTokenUrl,
	        String serverAuthTokenUrl,
	        String clientId,
	        String clientSecret,
	        List<String> scopes) throws IllegalArgumentException {
		this(serverLoginTokenUrl, serverAuthTokenUrl, new OAuthClientConfigImpl(clientId, clientSecret), DEFAULT_STATE,
		        scopes);
	}

	public AbstractOAuthService(String serverLoginTokenUrl,
	        String serverAuthTokenUrl,
	        OAuthClientConfig clientConfig,
	        List<String> scopes) throws IllegalArgumentException {
		this(serverLoginTokenUrl, serverAuthTokenUrl, clientConfig, DEFAULT_STATE, scopes);
	}

	public AbstractOAuthService(String serverLoginTokenUrl,
	        String serverAuthTokenUrl,
	        OAuthClientConfig clientConfig,
	        String state,
	        List<String> scopes) throws IllegalArgumentException {
		this.setServerLoginTokenUrl(serverLoginTokenUrl);
		this.setServerAuthTokenUrl(serverAuthTokenUrl);
		this.setClient(clientConfig);
		this.setState(state);
		this.setScopes(scopes);
	}

	public String getServerLoginTokenUrl() {
		return this.serverLoginTokenUrl;
	}

	public void setServerLoginTokenUrl(String serverLoginTokenUrl) {
		this.serverLoginTokenUrl = serverLoginTokenUrl;
	}

	public String getServerAuthTokenUrl() {
		return this.serverAuthTokenUrl;
	}

	public void setServerAuthTokenUrl(String serverAuthTokenUrl) {
		this.serverAuthTokenUrl = serverAuthTokenUrl;
	}

	public OAuthClientConfig getClientConfig() {
		return this.client;
	}

	public void setClient(OAuthClientConfig client) throws IllegalArgumentException {
		if (client == null) {
			throw new IllegalArgumentException("Client cannot be null.");
		}

		this.client = client;
	}

	public String getLoginTokenRedirectUrl() {
		return this.loginTokenRedirectUrl;
	}

	public void setLoginTokenRedirectUrl(String loginTokenRedirectUrl) {
		this.loginTokenRedirectUrl = loginTokenRedirectUrl;
	}

	public String getAuthTokenRedirectUrl() {
		return this.authTokenRedirectUrl;
	}

	public void setAuthTokenRedirectUrl(String authTokenRedirectUrl) {
		this.authTokenRedirectUrl = authTokenRedirectUrl;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<String> getScopes() {
		return this.scopes;
	}

	public void setScopes(List<String> scopes) {
		this.scopes = scopes;
	}

	// MARK: Internal
	protected String getFullRequestUrl(HttpServletRequest request) {
		StringBuffer fullUrlBuf = request.getRequestURL();

		if (request.getQueryString() != null) {
			fullUrlBuf.append('?').append(request.getQueryString());
		}

		return fullUrlBuf.toString();
	}

	protected void handleLoginConnectionException(IOException e,
	                                              HttpURLConnection connection) {
		try {
			Integer code = connection.getResponseCode();
			String message = connection.getResponseMessage();
			throw new OAuthConnectionException(code.toString(), message);
		} catch (IOException ioe) {
			throw new OAuthConnectionException(e);
		}
	}

}
