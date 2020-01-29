package com.dereekb.gae.server.auth.security.token.model;

import com.dereekb.gae.server.auth.model.login.Login;
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

	/**
	 * Builds a token from the input {@link LoginPointer}.
	 *
	 * @param pointer
	 *            {@link LoginPointer}. Never {@code null}.
	 * @param options
	 *            {@link LoginTokenBuilderOptions}. Never {@code null}.
	 * @return {@link LoginToken}. Never {@code null}.
	 */
	public T buildLoginToken(LoginPointer pointer,
	                         LoginTokenBuilderOptions options);

	/**
	 * Builds a token from the input {@link LoginPointer} and {@link Login}. The
	 * pointer should reference the login.
	 *
	 * @param pointer
	 *            {@link LoginPointer}. Never {@code null}.
	 * @param login
	 *            {@link Login} for the pointer. May be {@code null} if the
	 *            pointer has no login yet.
	 * @param refreshAllowed
	 *            whether or not refreshing is allowed with the result token.
	 * @return {@link LoginToken}. Never {@code null}.
	 */
	public T buildLoginToken(LoginPointer pointer,
	                         Login login,
	                         boolean refreshAllowed);

	/**
	 * Builds a token from the input {@link LoginPointer} and {@link Login}. The
	 * pointer should reference the login.
	 *
	 * @param pointer
	 *            {@link LoginPointer}. Never {@code null}.
	 * @param login
	 *            {@link Login} for the pointer. May be {@code null} if the
	 *            pointer has no login yet.
	 * @param options
	 *            {@link LoginTokenBuilderOptions}. Never {@code null}.
	 * @return {@link LoginToken}. Never {@code null}.
	 */
	public T buildLoginToken(LoginPointer pointer,
	                         Login login,
	                         LoginTokenBuilderOptions options);

}
