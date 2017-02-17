package com.dereekb.gae.server.auth.security.login.oauth.impl.service.scribe;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthCode;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthClientConfig;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthUnauthorizedClientException;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuth2AccessTokenErrorResponse;
import com.github.scribejava.core.model.OAuth2AccessTokenErrorResponse.ErrorCode;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
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

	private String stateSecret = "/login";

	private OAuth20Service scribeService;

	private OAuthClientConfig clientConfig;

	private List<String> scopes;

	public AbstractScribeOAuthService(OAuthClientConfig clientConfig, List<String> scopes)
	        throws IllegalArgumentException {
		this.setClientConfig(clientConfig);
		this.setScopes(scopes);
		this.initService();
	}

	public OAuthClientConfig getClientConfig() {
		return this.clientConfig;
	}

	public void setClientConfig(OAuthClientConfig clientConfig) throws IllegalArgumentException {
		if (clientConfig == null) {
			throw new IllegalArgumentException("ClientConfig cannot be null.");
		}

		this.clientConfig = clientConfig;
	}

	public OAuth20Service getScribeService() {
		return this.scribeService;
	}

	public List<String> getScopes() {
		return this.scopes;
	}

	public void setScopes(List<String> scopes) throws IllegalArgumentException {
		if (scopes == null) {
			throw new IllegalArgumentException("Scopes cannot be null.");
		}

		this.scopes = scopes;
	}

	// MARK: Internal
	private void initService() {
		this.scribeService = this.buildScribeService();
	}

	protected OAuth20Service buildScribeService() {
		ServiceBuilder builder = new ServiceBuilder();

		builder.apiKey(this.clientConfig.getClientId());
		builder.apiSecret(this.clientConfig.getClientSecret());
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

	protected String getAuthorizationCodeFromRequest(HttpServletRequest request) {
		throw new UnsupportedOperationException("Responses should be handled by client first.");
	}

	@Override
	public OAuthAuthorizationInfo processAuthorizationCode(OAuthAuthCode code)
	        throws OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException {
		String authCode = code.getAuthCode();
		return this.processAuthorizationCode(authCode);
	}

	protected OAuthAuthorizationInfo processAuthorizationCode(String code)
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
		return this.retrieveAuthorizationInfo(accessToken);
	}

	protected void handleTokenOAuthException(OAuthException e) throws OAuthAuthorizationTokenRequestException {

		if (OAuth2AccessTokenErrorResponse.class.isAssignableFrom(e.getClass())) {
			OAuth2AccessTokenErrorResponse tokenError = (OAuth2AccessTokenErrorResponse) e;

			ErrorCode errorCode = tokenError.getErrorCode();
			String errorDescription = tokenError.getErrorDescription();

			switch (errorCode) {
				case unauthorized_client:
					throw new OAuthUnauthorizedClientException();
				default:
					throw new OAuthAuthorizationTokenRequestException(errorCode.toString(), errorDescription);
			}
		}
	}

	// MARK: Authorization Info
	@Override
	public OAuthAuthorizationInfo retrieveAuthorizationInfo(OAuthAccessToken token)
	        throws OAuthAuthorizationTokenRequestException,
	            OAuthConnectionException {
		return this.getLoginInfoFromServer(token);
	}

	private OAuthAuthorizationInfo getLoginInfoFromServer(OAuthAccessToken token)
	        throws OAuthAuthorizationTokenRequestException,
	            OAuthConnectionException {

		OAuthAuthorizationInfo result = null;

		OAuthRequest request = this.buildRequest(token);

		OAuth2AccessToken accessToken = new OAuth2AccessToken(token.getAccessToken());
		this.scribeService.signRequest(accessToken, request);

		try {
			Response response = this.scribeService.execute(request);
			String data = response.getBody();
			result = this.parseResultFromData(token, data);
		} catch (IOException e) {
			throw new OAuthConnectionException(e);
		} catch (Exception e) {
			throw new OAuthException(e);
		}

		return result;
	}

	protected abstract String getLoginInfoRequestUrl(OAuthAccessToken token);

	protected OAuthRequest buildRequest(OAuthAccessToken token) {
		return new OAuthRequest(Verb.GET, this.getLoginInfoRequestUrl(token));
	}

	protected abstract OAuthAuthorizationInfo parseResultFromData(OAuthAccessToken token,
	                                                              String data)
	        throws Exception;

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
