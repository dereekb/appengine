package com.dereekb.gae.server.auth.security.login.oauth.impl.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.common.base.Joiner;

/**
 * {@link OAuthService} implementation using ScribeJava.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractScribeOAuthService
        implements OAuthService {

	private static final String SCOPES_SPLITTER = " ";

	private OAuth20Service scribeService = this.buildScribeService();

	private String stateSecret = "/login";

	private String clientKey;
	private String clientSecret;

	private List<String> scopes;

	public AbstractScribeOAuthService(String clientKey, String clientSecret, List<String> scopes) {
		this.setClientKey(clientKey);
		this.setClientSecret(clientSecret);
		this.setScopes(scopes);
		this.initService();
	}

	public String getClientKey() {
		return this.clientKey;
	}

	public void setClientKey(String clientKey) throws IllegalArgumentException {
		if (clientKey == null) {
			throw new IllegalArgumentException("ClientKey cannot be null.");
		}

		this.clientKey = clientKey;
	}

	public String getClientSecret() {
		return this.clientSecret;
	}

	public void setClientSecret(String clientSecret) throws IllegalArgumentException {
		if (clientSecret == null) {
			throw new IllegalArgumentException("ClientSecret cannot be null.");
		}

		this.clientSecret = clientSecret;
	}

	public OAuth20Service getScribeService() {
		return this.scribeService;
	}

	public List<String> getScopes() {
		return this.scopes;
	}

	public void setScopes(List<String> scopes) {
		if (scopes == null) {
			throw new IllegalArgumentException("scopes cannot be null.");
		}

		this.scopes = scopes;
	}

	// MARK: Internal
	private void initService() {
		this.scribeService = this.buildScribeService();
	}

	protected OAuth20Service buildScribeService() {
		ServiceBuilder builder = new ServiceBuilder();

		builder.apiKey(this.clientKey);
		builder.apiSecret(this.clientSecret);
		builder.state(this.stateSecret);
		builder.scope(this.getScopesString());

		return this.buildScribeService(builder);
	}

	protected abstract OAuth20Service buildScribeService(ServiceBuilder builder);

	protected String getScopesString() {
		return Joiner.on(SCOPES_SPLITTER).skipNulls().join(this.scopes);
	}

	// MARK: OAuthService
	@Override
	public abstract LoginPointerType getLoginType();

	@Override
	public String getAuthorizationCodeRequestUrl() {
		return this.scribeService.getAuthorizationUrl();
	}

	@Override
	public OAuthAuthorizationInfo processAuthorizationCodeResponse(HttpServletRequest request)
	        throws OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException {
		String code = this.getAuthorizationCodeFromRequest(request);
		return this.processAuthorizationCode(code);
	}

	protected abstract String getAuthorizationCodeFromRequest(HttpServletRequest request);

	@Override
	public OAuthAuthorizationInfo processAuthorizationCode(String code)
	        throws OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException {
		OAuth2AccessToken scribeAccessToken = null;

		try {
			scribeAccessToken = this.scribeService.getAccessToken(code);
		} catch (IOException | InterruptedException | ExecutionException e) {
			throw new OAuthConnectionException(e);
		} catch (OAuthException e) {
			this.handleTokenOAuthException(e);

			// Force throw exception if one wasn't thrown.
			throw new OAuthAuthorizationTokenRequestException(e);
		}

		ScribeOAuthAccessToken accessToken = new ScribeOAuthAccessToken(scribeAccessToken);
		return this.getAuthorizationInfo(accessToken);
	}

	public void handleTokenOAuthException(OAuthException e) throws OAuthAuthorizationTokenRequestException {
		throw new OAuthAuthorizationTokenRequestException(e);
	}

	/**
	 * {@link OAuthAccessToken} implementation for ScribeJava.
	 * 
	 * @author dereekb
	 *
	 */
	public static class ScribeOAuthAccessToken
	        implements OAuthAccessToken {

		private final OAuth2AccessToken accessToken;

		public ScribeOAuthAccessToken(OAuth2AccessToken accessToken) {
			this.accessToken = accessToken;
		}

		// MARK: OAuthAccessToken
		@Override
		public String getAccessToken() {
			return this.accessToken.getRawResponse();
		}

		@Override
		public String getRefreshToken() {
			return this.accessToken.getRefreshToken();
		}

		@Override
		public Long getExpiresIn() {
			Integer expiresIn = this.accessToken.getExpiresIn();
			return new Long(expiresIn);
		}

	}

}
