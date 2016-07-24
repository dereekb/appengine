package com.dereekb.gae.web.api.auth.controller.oauth;

import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthServiceUnavailableException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * Delegate for {@link OAuthLoginController}.
 *
 * @author dereekb
 *
 */
public interface OAuthLoginControllerDelegate {

	/**
	 * Performs a login using the access token.
	 *
	 * @param type
	 *            oauth type. Never {@code null},
	 * @param accessToken
	 *            token string. Never {@code null}.
	 * @return {@link LoginTokenPair} with valid login. Never {@code null}.
	 *
	 * @throws OAuthInsufficientException
	 * @throws OAuthServiceUnavailableException
	 * @throws OAuthConnectionException
	 */
	public LoginTokenPair login(String type,
	                            String accessToken)
	        throws OAuthInsufficientException,
	            OAuthAuthorizationTokenRequestException,
	            OAuthServiceUnavailableException,
	            OAuthConnectionException;

}
