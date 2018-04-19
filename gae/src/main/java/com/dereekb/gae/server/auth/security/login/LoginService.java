package com.dereekb.gae.server.auth.security.login;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;

/**
 * Service used for retrieving {@link LoginPointer} values using a username
 * string.
 *
 * @author dereekb
 *
 */
public interface LoginService {

	/**
	 * Retrieves a {@link LoginPointer} with the given username if it exists.
	 *
	 * @param username
	 *            Username. Never {@code null}.
	 * @return {@Link LoginPointer} if it exists.
	 * @throws LoginUnavailableException
	 *             Thrown if the {@link LoginPointer} for the username does not
	 *             exist.
	 */
	public LoginPointer getLogin(String username) throws LoginUnavailableException;

}
