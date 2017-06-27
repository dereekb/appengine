package com.dereekb.gae.server.auth.security.token.filter;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;

/**
 * Used for asserting valid {@link DecodeDLoginToken} values.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenAuthenticationFilterVerifier {

	/**
	 * Asserts that only system requests are passed.
	 * 
	 * @param decodedLoginToken
	 *            {@link DecodedLoginToken}. Never {@code null}.
	 * @param request
	 *            {@link HttpServletRequest}. Never {@code null}.
	 * @throws TokenException
	 *             thrown if the token is invalid.
	 */
	public void assertValidDecodedLoginToken(DecodedLoginToken decodedLoginToken,
	                                         HttpServletRequest request)
	        throws TokenException;

}
