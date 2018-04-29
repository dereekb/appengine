package com.dereekb.gae.server.auth.security.login.password;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.exception.InvalidLoginCredentialsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.auth.security.login.password.exception.PasswordRestrictionException;

/**
 * Service used for creating and retrieving {@link LoginPointer} instances using
 * a username/password.
 *
 * @author dereekb
 *
 */
public interface PasswordLoginService {

	/**
	 * Creates a new {@link LoginPointer} with the specified credentials.
	 *
	 * @param pair
	 *            {@link PasswordLoginPair}. Never {@code null}.
	 * @return {@link LoginPointer} for the newly created element.
	 * @throws LoginExistsException
	 *             Thrown if a login with this identifier already exists.
	 * @throws PasswordRestrictionException
	 *             thrown if the password does not pass the restrictions.
	 */
	public LoginPointer create(PasswordLoginPair pair) throws LoginExistsException, PasswordRestrictionException;

	/**
	 * Retrieves a {@link LoginPointer} for the given username, if the password
	 * matches.
	 *
	 * @param pair
	 *            {@link PasswordLoginPair}. Never {@code null}.
	 * @return {@link LoginPointer} for the input credentials.
	 * @throws LoginUnavailableException
	 * @throws InvalidLoginCredentialsException
	 */
	public LoginPointer login(PasswordLoginPair pair)
	        throws LoginUnavailableException,
	            InvalidLoginCredentialsException;

}
