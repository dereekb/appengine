package com.dereekb.gae.web.api.auth.controller.password;

import com.dereekb.gae.server.auth.security.login.exception.InvalidLoginCredentialsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginPair;
import com.dereekb.gae.server.auth.security.login.password.recover.PasswordRecoveryService;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * Delegate for {@link PasswordLoginController} used for creating and attempting
 * logins.
 *
 * @author dereekb
 *
 */
public interface PasswordLoginControllerDelegate
        extends PasswordRecoveryService {

	/**
	 * Attempts to create a new login. On success, will return a
	 * {@link LoginTokenPair}.
	 *
	 * @param pair
	 *            {@link PasswordLoginPair}. Never {@code null}.
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 * @throws LoginExistsException
	 *             thrown if a login with the username already exists.
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
	 *             thrown if the wrong username/password pair is entered.
	 * @throws LoginUnavailableException
	 *             thrown if the login is not available.s
	 */
	public LoginTokenPair login(PasswordLoginPair pair)
	        throws LoginUnavailableException,
	            InvalidLoginCredentialsException;

}
