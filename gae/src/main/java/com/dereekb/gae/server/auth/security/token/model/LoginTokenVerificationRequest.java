package com.dereekb.gae.server.auth.security.token.model;

import javax.servlet.http.HttpServletRequest;

/**
 * {@link LoginTokenVerifier} request.
 *
 * @author dereekb
 *
 */
public interface LoginTokenVerificationRequest {

	/**
	 * Returns the token to verify.
	 *
	 * @return {@link EncodedLoginToken}. Never {@code null}.
	 */
	public EncodedLoginToken getLoginToken();

	/**
	 * Returns the request.
	 *
	 * @return {@link HttpServletRequest}. Never {@code null}.
	 */
	public HttpServletRequest getRequest();

}
