package com.dereekb.gae.server.auth.security.login.password;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginService;
import com.dereekb.gae.server.auth.security.login.exception.InvalidLoginCredentialsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;

/**
 * Service used for creating and retrieving {@link LoginPointer} instances using
 * a username/password.
 *
 * @author dereekb
 *
 */
public interface PasswordLoginService extends LoginService {

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

	/**
	 * Creates a new {@link LoginPointer} with the specified credentials.
	 *
	 * @param pair
	 *            {@link PasswordLoginPair}. Never {@code null}.
	 * @return {@link LoginPointer} for the newly created element.
	 * @throws LoginExistsException
	 *             Thrown if a login with this identifier already exists.
	 */
	public LoginPointer create(PasswordLoginPair pair) throws LoginExistsException;

}
