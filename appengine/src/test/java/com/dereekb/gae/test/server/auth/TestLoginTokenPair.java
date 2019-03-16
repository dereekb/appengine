package com.dereekb.gae.test.server.auth;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;

/**
 * Test token pair.
 * 
 * @author dereekb
 *
 */
public interface TestLoginTokenPair extends EncodedLoginToken {

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

}
