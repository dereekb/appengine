package com.dereekb.gae.server.auth.security.login.password;

import com.dereekb.gae.server.auth.security.login.password.exception.PasswordRestrictionException;

/**
 * A password restriction.
 *
 * @author dereekb
 *
 */
public interface PasswordRestriction {

	/**
	 * Asserts the input password is valid.
	 *
	 * @param password
	 *            {@link String}. Never {@code null}.
	 * @throws PasswordRestrictionException
	 */
	public void assertIsValidPassword(String password) throws PasswordRestrictionException;

}
