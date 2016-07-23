package com.dereekb.gae.server.auth.security.login.oauth.impl.service;

import java.util.List;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Abstract {@link OAuthService} implementation.
 *
 * @author dereekb
 *
 */
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

	private String clientId = null;
	private String clientSecret = null;

	private String loginTokenRedirectUrl = null;
	private String authTokenRedirectUrl = DEFAULT_AUTH_TOKEN_REDIRECT_URL;

	private String state = DEFAULT_STATE;

	private String authorizationGrantType = "authorization_code";
	private boolean allowRefreshCredentials = false;

	private List<String> scopes;

	public AbstractOAuthService(String serverLoginTokenUrl,
	        String serverAuthTokenUrl,
	        String clientId,
	        String clientSecret,
	        List<String> scopes) throws IllegalArgumentException {
		this(serverLoginTokenUrl, serverAuthTokenUrl, clientId, clientSecret, DEFAULT_STATE, scopes);
	}

	public AbstractOAuthService(String serverLoginTokenUrl,
	        String serverAuthTokenUrl,
	        String clientId,
	        String clientSecret,
	        String state,
	        List<String> scopes) throws IllegalArgumentException {
		this.setServerLoginTokenUrl(serverLoginTokenUrl);
		this.setServerAuthTokenUrl(serverAuthTokenUrl);
		this.setClientId(clientId);
		this.setClientSecret(clientSecret);
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

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return this.clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
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

	public String getAuthorizationGrantType() {
		return this.authorizationGrantType;
	}

	public void setAuthorizationGrantType(String authorizationGrantType) {
		this.authorizationGrantType = authorizationGrantType;
	}

	public boolean isAllowRefreshCredentials() {
		return this.allowRefreshCredentials;
	}

	public void setAllowRefreshCredentials(boolean allowRefreshCredentials) {
		this.allowRefreshCredentials = allowRefreshCredentials;
	}

	public List<String> getScopes() {
		return this.scopes;
	}

	public void setScopes(List<String> scopes) {
		this.scopes = scopes;
	}

	// MARK: OAuthLoginService
	@Override
	public String getAuthorizationCodeRequestUrl() {
		GoogleAuthorizationCodeRequestUrl request = this.getAuthorizationCodeRequest();

		if (this.state != null) {
			request = request.setState(DEFAULT_STATE);
		}

		String url = request.build();
		return url;
	}

	public GoogleAuthorizationCodeRequestUrl getAuthorizationCodeRequest() {
		return new GoogleAuthorizationCodeRequestUrl(this.serverLoginTokenUrl, this.clientId,
		        this.loginTokenRedirectUrl,
		        this.scopes);
	}

	// MARK: Extension
	public abstract OAuthAccessToken getAuthorizationToken(String authCode)
	        throws OAuthAuthorizationTokenRequestException,
	            OAuthConnectionException;

	// MARK: Internal
	protected BasicAuthentication getClientServerAuthentication() {
		return new BasicAuthentication(this.clientId, this.clientSecret);
	}

	protected AuthorizationCodeTokenRequest makeAuthorizationCodeTokenRequest(String authCode) {

		GenericUrl serverUrl = new GenericUrl(this.serverAuthTokenUrl);
		BasicAuthentication clientServerAuthentication = this.getClientServerAuthentication();

		return new AuthorizationCodeTokenRequest(new NetHttpTransport(), new JacksonFactory(), serverUrl, authCode)
		        .setRedirectUri(this.authTokenRedirectUrl).setClientAuthentication(clientServerAuthentication)
		        .setGrantType(this.authorizationGrantType);
	}

	/**
	 * Automatically builds a credential using the {@link OAuthAccessToken}. If
	 * it contains a refresh token, a full {@link Credential} will be generated
	 * using {@link #buildFullCredential()}
	 *
	 * @param accessToken
	 * @return
	 */
	protected Credential buildCredential(OAuthAccessToken token) {
		String accessToken = token.getAccessToken();
		String refreshToken = token.getRefreshToken();
		Long expiration = token.getExpiration();

		Credential credential = null;

		if (this.allowRefreshCredentials && refreshToken != null) {
			credential = this.buildFullCredential(accessToken, refreshToken, expiration);
		} else {
			credential = this.buildAccessTokenCredential(accessToken, expiration);
		}

		return credential;
	}

	protected Credential buildAccessTokenCredential(String accessToken,
	                                                Long expiration) {
		return new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken)
		        .setExpiresInSeconds(expiration);
	}

	protected Credential buildFullCredential(String accessToken,
	                                         String refreshToken,
	                                         Long expiration) {
		GenericUrl serverUrl = new GenericUrl(this.serverAuthTokenUrl);
		BasicAuthentication clientServerAuthentication = this.getClientServerAuthentication();

		Credential credential = new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
		        .setTransport(new NetHttpTransport()).setJsonFactory(new JacksonFactory()).setTokenServerUrl(serverUrl)
		        .setClientAuthentication(clientServerAuthentication).build();

		credential.setAccessToken(accessToken);
		credential.setRefreshToken(refreshToken);
		credential.setExpiresInSeconds(expiration);

		return credential;
	}

	@Override
	public String toString() {
		return "AbstractOAuthService [serverLoginTokenUrl=" + this.serverLoginTokenUrl + ", serverAuthTokenUrl="
		        + this.serverAuthTokenUrl + ", clientId=" + this.clientId + ", clientSecret=" + this.clientSecret
		        + ", loginTokenRedirectUrl=" + this.loginTokenRedirectUrl + ", authTokenRedirectUrl="
		        + this.authTokenRedirectUrl + ", state=" + this.state + ", authorizationGrantType="
		        + this.authorizationGrantType + ", allowRefreshCredentials=" + this.allowRefreshCredentials
		        + ", scopes=" + this.scopes + "]";
	}

}
