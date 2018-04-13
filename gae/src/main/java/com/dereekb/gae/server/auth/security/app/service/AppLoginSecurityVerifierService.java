package com.dereekb.gae.server.auth.security.app.service;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;

/**
 * Used for verifying an app request.
 *
 * @author dereekb
 *
 */
public interface AppLoginSecurityVerifierService {

	/**
	 * Verifies a token signature.
	 *
	 * @param request
	 *            {@link LoginTokenVerifierRequest}. Never {@code null}.
	 * @return {@code true} if valid.
	 */
	public boolean isValidTokenSignature(LoginTokenVerifierRequest request);

	/**
	 * Asserts that a token is valid.
	 *
	 * @param request
	 *            {@link LoginTokenVerifierRequest}. Never {@code null}.
	 */
	public void assertValidTokenSignature(LoginTokenVerifierRequest request) throws TokenException;

}
