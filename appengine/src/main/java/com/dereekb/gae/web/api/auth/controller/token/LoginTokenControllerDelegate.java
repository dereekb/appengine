package com.dereekb.gae.web.api.auth.controller.token;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.refresh.exception.RefreshTokenExpiredException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.time.exception.RateLimitException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * {@link LoginTokenController} delegate.
 *
 * @author dereekb
 *
 */
public interface LoginTokenControllerDelegate {

	/**
	 * Creates a new refresh token with the input token.
	 *
	 * @param token
	 *            {@link EncodedLoginToken}. Never {@code null}.
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 * @throws TokenUnauthorizedException
	 *             thrown if the token is not authorized.
	 */
	public LoginTokenPair makeRefreshToken(EncodedLoginToken token) throws TokenUnauthorizedException;

	/**
	 * Authenticates with the input refresh token.
	 *
	 * @param refreshToken
	 *            {@link EncodedLoginToken}. Never {@code null}.
	 * @param rolesMask
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 * @throws RefreshTokenExpiredException
	 *             thrown if the token has expired.
	 */
	public LoginTokenPair loginWithRefreshToken(EncodedLoginToken refreshToken,
	                                            Long rolesMask)
	        throws RefreshTokenExpiredException;

	/**
	 * Resets token authentication for the current user, or the target user,
	 * causing refresh tokens to no longer work.
	 *
	 * @param loginKey
	 *            Optional {@link String} key to reset.
	 * @return {@link ModelKey} of login that was updated.
	 * @throws NoSecurityContextException
	 *             thrown if no security is available.
	 * @throws TokenUnauthorizedException
	 *             thrown if the token is not authorized.
	 * @throws UnavailableModelException
	 *             thrown if the target login is not available.
	 * @throws RateLimitException
	 *             thrown if the authentication was recently reset.
	 */
	public ModelKey resetAuthentication(String loginKey)
	        throws NoSecurityContextException,
	            TokenUnauthorizedException,
	            UnavailableModelException,
	            RateLimitException;

	/**
	 * Verifies/Validates the input, and returns a response for the client.
	 *
	 * @param request
	 *            {@link TokenValidationRequest}. Never {@code null}.
	 * @return {@link ApiResponseImpl}. Never {@code null}.
	 */
	public ApiResponseImpl validateToken(TokenValidationRequest request) throws TokenException;

}
