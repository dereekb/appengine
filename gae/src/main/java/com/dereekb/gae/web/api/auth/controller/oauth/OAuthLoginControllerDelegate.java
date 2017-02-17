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
	 * Performs a login using an authentication code. This server will work to
	 * 
	 * @param type
	 *            oauth type. Never {@code null},
	 * @param authCode
	 *            token string. Never {@code null}.
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 * 
	 * @throws OAuthConnectionException
	 * @throws OAuthInsufficientException
	 * @throws OAuthAuthorizationTokenRequestException
	 * @throws OAuthServiceUnavailableException
	 */
	public LoginTokenPair loginWithAuthCode(String type,
	                                        String authCode)
	        throws OAuthConnectionException,
	            OAuthInsufficientException,
	            OAuthAuthorizationTokenRequestException,
	            OAuthServiceUnavailableException;

	/**
	 * Performs a login using the access token.
	 *
	 * @param type
	 *            oauth type. Never {@code null},
	 * @param accessToken
	 *            token string. Never {@code null}.
	 * @return {@link LoginTokenPair} with valid login. Never {@code null}.
	 *
	 * @throws OAuthConnectionException
	 * @throws OAuthInsufficientException
	 * @throws OAuthAuthorizationTokenRequestException
	 * @throws OAuthServiceUnavailableException
	 */
	public LoginTokenPair loginWithAccessToken(String type,
	                                           String accessToken)
	        throws OAuthConnectionException,
	            OAuthInsufficientException,
	            OAuthAuthorizationTokenRequestException,
	            OAuthServiceUnavailableException;

}
