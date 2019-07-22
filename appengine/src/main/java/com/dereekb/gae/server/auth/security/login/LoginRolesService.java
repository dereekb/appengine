package com.dereekb.gae.server.auth.security.login;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Service used for managing the admin roles state of an account.
 *
 * @author dereekb
 *
 */
public interface LoginRolesService {

	/**
	 * Makes the {@link Login} an admin.
	 *
	 * @param key {@link ModelKey} of the {@link Login}. Never {@code null}.
	 * @return {@link Login}. Never {@code null}.
	 * @throws LoginUnavailableException
	 *             thrown if the login is unavailable.
	 */
	public Login makeAdmin(ModelKey key) throws LoginUnavailableException;

	/**
	 * Removes the {@link Login} roles.
	 *
	 * @param key {@link ModelKey} of the {@link Login}. Never {@code null}.
	 * @return {@link Login}. Never {@code null}.
	 * @throws LoginUnavailableException
	 *             thrown if the login is unavailable.
	 */
	public Login clearRoles(ModelKey key) throws LoginUnavailableException;

}
