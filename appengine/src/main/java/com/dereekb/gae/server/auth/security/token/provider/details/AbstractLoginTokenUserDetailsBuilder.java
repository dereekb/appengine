package com.dereekb.gae.server.auth.security.token.provider.details;

import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * Used for building user details.
 * 
 * @author dereekb
 *
 * @param <T>
 *            token type
 */
public interface AbstractLoginTokenUserDetailsBuilder<D extends LoginTokenUserDetails<T>, T extends LoginToken> {

	/**
	 * Builds a new {@link LoginTokenUserDetails} instance from the
	 * {@link LoginToken}.
	 *
	 * @param loginToken
	 *            {@link LoginToken}. Never {@code null}.
	 * @return {@link LoginTokenUserDetails}. Never {@code null}.
	 */
	public D buildDetails(DecodedLoginToken<T> loginToken);

}
