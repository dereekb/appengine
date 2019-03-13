package com.dereekb.gae.server.auth.security.token.filter;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * Used for asserting valid {@link DecodedLoginToken} values against the
 * request.
 * <p>
 * Generally this means verifying the signature header.
 *
 * @author dereekb
 *
 */
public interface LoginTokenAuthenticationFilterVerifier<T extends LoginToken> {

	/**
	 * Asserts that the token is valid for the request.
	 *
	 * @param decodedLoginToken
	 *            {@link DecodedLoginToken}. Never {@code null}.
	 * @param request
	 *            {@link HttpServletRequest}. Never {@code null}.
	 * @throws TokenException
	 *             thrown if the token is invalid.
	 */
	public void assertValidDecodedLoginToken(DecodedLoginToken<T> decodedLoginToken,
	                                         HttpServletRequest request)
	        throws TokenException;

}
