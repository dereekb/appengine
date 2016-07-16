package com.dereekb.gae.test.server.auth;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * Context used for setting, clearing, and manipulating a test token.
 *
 * @author dereekb
 *
 */
public interface TestLoginTokenContext {

	/**
	 * @return {@link String} JWT token. May be {@code null}.
	 */
	public String getToken();

	/**
	 * Generates a new login, and login pointer.
	 */
	public LoginPointer generateLogin();

	public void setLogin(LoginToken token);

	public void setLogin(LoginPointer pointer);

}
