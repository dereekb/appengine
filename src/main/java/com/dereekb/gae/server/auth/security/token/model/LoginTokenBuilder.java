package com.dereekb.gae.server.auth.security.token.model;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Used for building a {@link LoginToken} from a {@link LoginPointer} for
 * authentication.
 *
 * @author dereekb
 *
 */
public interface LoginTokenBuilder<T extends LoginToken> {

	/**
	 * Builds an anonymous login token using the input id.
	 * 
	 * @param anonymousId
	 *            Optional anonymous identifier. May be {@code null}.
	 * @return {@link LoginToken}. Never {@code null}.
	 */
	public T buildAnonymousLoginToken(String anonymousId);

	/**
	 * Builds a token from the input {@link LoginPointer}.
	 * 
	 * @param pointer
	 *            {@link LoginPointer}. Never {@code null}.
	 * @param refreshAllowed
	 *            whether or not refreshing is allowed with the result token.
	 * @return {@link LoginToken}. Never {@code null}.
	 */
	public T buildLoginToken(LoginPointer pointer,
	                         boolean refreshAllowed);

}