package com.dereekb.gae.server.auth.security.login.oauth.impl.service.facebook;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.AbstractScribeOAuthService;
import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.apis.facebook.FacebookAccessTokenErrorResponse;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.oauth.OAuth20Service;

/**
 * {@link OAuthService} implementation for Facebook OAuth.
 * 
 * @author dereekb
 *
 */
public class FacebookOAuthService extends AbstractScribeOAuthService {

	private static final List<String> FACEBOOK_OAUTH_SCOPES = Arrays.asList("email");

	public FacebookOAuthService(String clientId, String clientSecret) throws IllegalArgumentException {
		super(clientId, clientSecret, FACEBOOK_OAUTH_SCOPES);
	}

	// MARK: OAuthService
	@Override
	public LoginPointerType getLoginType() {
		return LoginPointerType.OAUTH_FACEBOOK;
	}

	// MARK: ScribeOAuthService
	@Override
	protected OAuth20Service buildScribeService(ServiceBuilder builder) {
		return builder.build(FacebookApi.instance());
	}

	@Override
	protected String getAuthorizationCodeFromRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleTokenOAuthException(OAuthException e) throws OAuthAuthorizationTokenRequestException {
		if (FacebookAccessTokenErrorResponse.class.isAssignableFrom(e.getClass())) {
			FacebookAccessTokenErrorResponse tokenError = (FacebookAccessTokenErrorResponse) e;

			String code = tokenError.getCode();
			String type = tokenError.getType();
			String message = tokenError.getMessage();

			throw new OAuthAuthorizationTokenRequestException(code, type, message, e);
		}
	}

	// MARK: Authorization Info
	@Override
	public OAuthAuthorizationInfo getAuthorizationInfo(OAuthAccessToken token)
	        throws OAuthAuthorizationTokenRequestException,
	            OAuthConnectionException {

		// TODO: Access facebook via HTTP with authorization code and get the
		// info we need.

		return null;
	}

}
