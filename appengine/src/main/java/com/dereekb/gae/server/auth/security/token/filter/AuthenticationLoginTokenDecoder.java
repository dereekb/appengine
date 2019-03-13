package com.dereekb.gae.server.auth.security.token.filter;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;

/**
 * Similar to {@link LoginTokenDecoder}, but
 *
 * @author dereekb
 *
 * @param <T>
 *            token type
 */
public interface AuthenticationLoginTokenDecoder<T extends LoginToken> {

	/**
	 * Decodes a token.
	 *
	 * @param token
	 *            Token string. Never {@code null}.
	 * @param request
	 *            {@link HttpServletRequest}. Never {@code null}.
	 * @return {@link LoginToken}. Never {@code null}.
	 * @throws TokenExpiredException
	 *             Thrown if the token was validated, but is considered expired.
	 * @throws TokenUnauthorizedException
	 *             Thrown if the token was unauthorized by either not existing
	 *             or not matching against the details.
	 * @throws TokenException
	 *             thrown if the token is invalid.
	 */
	public DecodedLoginToken<T> authenticateLoginToken(String token,
	                                                   HttpServletRequest request)
	        throws TokenExpiredException,
	            TokenUnauthorizedException,
	            TokenException;

}
