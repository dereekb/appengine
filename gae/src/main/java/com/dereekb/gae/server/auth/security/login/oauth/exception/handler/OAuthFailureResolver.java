package com.dereekb.gae.server.auth.security.login.oauth.exception.handler;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthenticationException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthDeniedException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthUnauthorizedClientException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginErrorException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginInvalidException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginRejectedException;

/**
 * Used to resolve a {@link OAuthAuthenticationException} and subtypes.
 * 
 * @author dereekb
 *
 */
public class OAuthFailureResolver {

	/**
	 * Will throw a new exception based on the input atomic operation exception.
	 *
	 * @param e
	 *            {@link AtomicOperationException}. Never {@code null}.
	 * @throws RuntimeException
	 *             thrown if the input type is
	 *             {@link AtomicOperationExceptionReason#EXCEPTION}.
	 */
	public static void resolve(OAuthAuthenticationException oauthException) throws RuntimeException {

		try {
			throw oauthException;
		} catch (OAuthAuthorizationTokenRequestException e) {
			throw new ApiLoginInvalidException(e);
		} catch (OAuthUnauthorizedClientException | OAuthDeniedException e) {
			throw new ApiLoginRejectedException(e);
		} catch (OAuthInsufficientException e) {
			throw new ApiLoginInvalidException(e);
		} catch (OAuthConnectionException e) {
			throw new ApiLoginErrorException(e);
		}

	}

}
