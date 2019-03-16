package com.dereekb.gae.server.auth.security.login.oauth;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;

/**
 * Used for creating or retrieving a {@link LoginPointer} using OAuth retrieve
 * credentials.
 *
 * @author dereekb
 *
 */
public interface OAuthLoginService {

	/**
	 * Retrieves or creates a new {@link LoginPointer} using the input
	 * {@link OAuthAuthorizationInfo}.
	 *
	 * @param authCode
	 *            {@link OAuthAuthorizationInfo}. Never {@code null}.
	 * @return {@link LoginPointer}. Never {@code null}.
	 * @throws OAuthInsufficientException
	 *             if the input is unacceptable.
	 */
	public LoginPointer login(OAuthAuthorizationInfo authorizationInfo) throws OAuthInsufficientException;

}
