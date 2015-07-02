package com.dereekb.gae.server.auth.security.registration.creator;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Interface for creating new {@link LoginPointer} instances.
 *
 * @author dereekb
 */
public interface LoginPointerCreator {

	/**
	 * Creates and saves a new {@link LoginPointer}, keyed by the passed
	 * authKey.
	 *
	 * The {@link Login} passed is also update to reflect it's new association
	 * with the created pointer.
	 *
	 * @param authKey
	 *            Key to use as an identifier.
	 * @param login
	 *            Login to attach the pointer to.
	 * @return a new {@link LoginPointer} identified by the passed authKey
	 *         variable.
	 */
	public LoginPointer createLoginPointer(String authKey,
	                                       Login login);

}
