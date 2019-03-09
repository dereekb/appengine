package com.dereekb.gae.server.auth.security.token.refresh;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.refresh.exception.AuthenticationPurgeException;
import com.dereekb.gae.server.auth.security.token.refresh.exception.RefreshTokenExpiredException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.time.exception.RateLimitException;

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
	 * @throws TokenUnauthorizedException
	 */
	public LoginPointer loadRefreshTokenPointer(LoginToken refreshToken)
	        throws RefreshTokenExpiredException,
	            TokenUnauthorizedException;

	/**
	 * Resets authentication for the referenced {@link Login}.
	 * 
	 * @throws UnavailableModelException
	 *             thrown if the target login is not available.
	 * @throws RateLimitException
	 *             thrown if the authentication was recently reset.
	 */
	public void resetAuthentication(ModelKey loginKey) throws UnavailableModelException, RateLimitException;

}
