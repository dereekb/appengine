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
	 * @return {@link LoginToken}. May be {@code null}.
	 */
	public LoginToken getLoginToken();

	/**
	 * Generates a new anonymous login token.
	 */
	public String generateAnonymousToken();

	/**
	 * Generates an anonymous login.
	 */
	public void generateAnonymousLogin();

	/**
	 * Generates a new system administrator with a random name.
	 */
	public TestLoginTokenPair generateSystemAdmin();

	/**
	 * Generates a new system administrator.
	 */
	public TestLoginTokenPair generateSystemAdmin(String username);

	/**
	 * Generates a new login, and login pointer.
	 */
	public TestLoginTokenPair generateLogin();

	/**
	 * Generates a new login, and login pointer using a specific username.
	 */
	public TestLoginTokenPair generateLogin(String username);

	/**
	 * Generates a new login, and login pointer using a specific username and
	 * roles.
	 */
	public TestLoginTokenPair generateLogin(String username,
	                                        Long roles);

	/**
	 * Clears the current login, be it anonymous or normal.
	 */
	public void clearLogin();

	public void setLogin(LoginToken token);

	public void setLogin(LoginPointer pointer);

}
