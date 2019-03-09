package com.dereekb.gae.server.auth.old.security.registration.creator;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.old.security.authentication.LoginTuple;

/**
 * Interface for creating new {@link Login} instances in the datastore.
 *
 * @author dereekb
 */
public interface LoginCreator {

	/**
	 * Creates a new permanent {@link Login} associated with a passed
	 * authentication key.
	 *
	 * @param authKey
	 *            Authentication key to be associated with the created login.
	 * @return a {@link LoginTuple} instance containing the newly created
	 *         elements.
	 */
	public LoginTuple newLogin(String authKey);

}
