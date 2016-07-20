package com.dereekb.gae.server.auth.security.login.oauth.impl;

import java.util.List;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
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
public abstract class AbstractOAuthLoginService
        implements OAuthService {

	private static final String DEFAULT_STATE = "/login";

	private String server = null;

	private String clientId = null;
	private String clientSecret = null;

	private String redirectUrl = null;
	private String state = DEFAULT_STATE;

	private String authorizationGrantType = "authorization_code";
	private boolean allowRefreshCredentials = false;

	private List<String> scopes;

	public AbstractOAuthLoginService(String server,
	        String clientId,
	        String clientSecret,
	        String redirectUrl,
	        List<String> scopes) throws IllegalArgumentException {
		this(server, clientId, clientSecret, redirectUrl, DEFAULT_STATE, scopes);
	}

	public AbstractOAuthLoginService(String server,
	        String clientId,
	        String clientSecret,
	        String redirectUrl,
	        String state,
	        List<String> scopes) throws IllegalArgumentException {
		this.setServer(server);
		this.setClientId(clientId);
		this.setClientSecret(clientSecret);
		this.setRedirectUrl(redirectUrl);
		this.setState(state);
		this.setScopes(scopes);
	}

	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
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

	public String getRedirectUrl() {
		return this.redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
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
		return new GoogleAuthorizationCodeRequestUrl(this.server, this.clientId, this.redirectUrl, this.scopes);
	}

	// MARK: Extension
	public abstract OAuthAccessToken getAuthorizationToken(String authCode)
	        throws OAuthAuthorizationTokenRequestException,
	            OAuthConnectionException;

	public abstract OAuthAuthorizationInfo getLoginInfo(OAuthAccessToken accessToken);

	// MARK: Internal
	protected BasicAuthentication getClientServerAuthentication() {
		return new BasicAuthentication(this.clientId, this.clientSecret);
	}

	protected AuthorizationCodeTokenRequest makeAuthorizationCodeTokenRequest(String authCode) {

		GenericUrl serverUrl = new GenericUrl(this.getServer());
		BasicAuthentication clientServerAuthentication = this.getClientServerAuthentication();

		return new AuthorizationCodeTokenRequest(new NetHttpTransport(), new JacksonFactory(), serverUrl, authCode)
		        .setRedirectUri(this.redirectUrl).setClientAuthentication(clientServerAuthentication)
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
		GenericUrl serverUrl = new GenericUrl(this.getServer());
		BasicAuthentication clientServerAuthentication = this.getClientServerAuthentication();

		Credential credential = new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
		        .setTransport(new NetHttpTransport()).setJsonFactory(new JacksonFactory()).setTokenServerUrl(serverUrl)
		        .setClientAuthentication(clientServerAuthentication).build();

		credential.setAccessToken(accessToken);
		credential.setRefreshToken(refreshToken);
		credential.setExpiresInSeconds(expiration);

		return credential;
	}

}
