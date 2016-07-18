package com.dereekb.gae.server.auth.security.login.oauth.impl;

import java.util.List;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;

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
	private String redirectUrl = null;
	private String state = DEFAULT_STATE;
	private List<String> scopes;

	public AbstractOAuthLoginService(String server,
	        String clientId,
	        String redirectUrl,
	        List<String> scopes) {
		this(server, clientId, redirectUrl, DEFAULT_STATE, scopes);
	}

	public AbstractOAuthLoginService(String server,
	        String clientId,
	        String redirectUrl,
	        String state,
	        List<String> scopes) {
		this.server = server;
		this.clientId = clientId;
		this.redirectUrl = redirectUrl;
		this.state = state;
		this.scopes = scopes;
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
	public abstract OAuthAccessToken getAuthorizationToken(String authCode);

	public abstract OAuthLoginInfo getLoginInfo(OAuthAccessToken accessToken);

}
