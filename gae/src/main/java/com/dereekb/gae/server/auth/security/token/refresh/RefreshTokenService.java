package com.dereekb.gae.server.auth.security.token.refresh;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.refresh.exception.AuthenticationPurgeException;
import com.dereekb.gae.server.auth.security.token.refresh.exception.RefreshTokenExpiredException;

/**
 * Service used for creating and building refresh tokens.
 * 
 * @author dereekb
 *
 */
public interface RefreshTokenService {

	/**
	 * Creates a new refresh token with the input {@link LoginToken}.
	 * <p>
	 * The refresh token should not be directly used for authentication
	 * purposes.
	 * 
	 * @param token
	 *            {@link LoginToken}. Never {@code null}.
	 * @return {@link LoginToken}. Never {@code null}.
	 * @throws AuthenticationPurgeException
	 * @throws TokenUnauthorizedException
	 */
	public LoginToken makeRefreshToken(LoginToken refreshToken)
	        throws AuthenticationPurgeException,
	            TokenUnauthorizedException;

	/**
	 * Verifies the input refresh token is valid. If valid, will return the
	 * {@link LoginPointer} used for authenticating again.
	 * 
	 * @param refreshToken
	 *            {@link LoginToken}. Never {@code null}.
	 * @return {@link LoginPointer}. Never {@code null}.
	 * 
	 * @throws RefreshTokenExpiredException
	 */
	public LoginPointer loadRefreshTokenPointer(LoginToken refreshToken) throws RefreshTokenExpiredException;

}
