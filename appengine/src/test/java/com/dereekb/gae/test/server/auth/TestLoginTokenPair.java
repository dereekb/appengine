package com.dereekb.gae.test.server.auth;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * Test token pair.
 *
 * @author dereekb
 *
 */
public interface TestLoginTokenPair
        extends EncodedLoginToken {

	/**
	 * Returns the login.
	 *
	 * @return {@link Login}. Never {@code null}.
	 */
	public Login getLogin();

	/**
	 * Returns the login pointer.
	 *
	 * @return {@link LoginPointer}. Never {@code null}.
	 */
	public LoginPointer getLoginPointer();

	/**
	 * Regenerates the login token.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String regenerateToken();

	/**
	 * Builds a new {@link LoginToken}.
	 *
	 * @return {@link LoginToken}. Never {@code null}.
	 */
	public LoginToken buildLoginToken();

}
