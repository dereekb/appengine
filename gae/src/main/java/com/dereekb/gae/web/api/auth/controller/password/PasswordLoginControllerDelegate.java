package com.dereekb.gae.web.api.auth.controller.password;

import com.dereekb.gae.server.auth.security.login.exception.InvalidLoginCredentialsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginPair;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * Delegate for {@link PasswordLoginController} used for creating and attempting
 * logins.
 *
 * @author dereekb
 *
 */
public interface PasswordLoginControllerDelegate {

	/**
	 * Attempts to create a new login. On success, will return a
	 * {@link LoginTokenPair}.
	 *
	 * @param pair
	 *            {@link PasswordLoginPair}. Never {@code null}.
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 * @throws LoginExistsException
	 */
	public LoginTokenPair createLogin(PasswordLoginPair pair) throws LoginExistsException;

	/**
	 * Attempts to login. On success, will return a {@link LoginTokenPair}
	 * result.
	 *
	 * @param pair
	 *            {@link PasswordLoginPair}. Never {@code null}.
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 * @throws InvalidLoginCredentialsException
	 * @throws LoginUnavailableException
	 */
	public LoginTokenPair login(PasswordLoginPair pair)
	        throws LoginUnavailableException,
	            InvalidLoginCredentialsException;

}
