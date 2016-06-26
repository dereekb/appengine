package com.dereekb.gae.server.auth.old.security.role;

import com.dereekb.gae.server.auth.model.login.Login;

/**
 * Used for editing {@link Role} values on a {@link Login}.
 *
 * @author dereekb
 */
public interface LoginRoleWriter {

	/**
	 * Clears all set {@link Role} values from the given {@link Login}.
	 *
	 * @param login
	 */
	public void clearRoles(Login login);

	/**
	 * Updates the {@link Login} using the input {@link LoginRoleChange}.
	 *
	 * @param login
	 * @param changes
	 */
	public void changeRoles(Login login,
	                        LoginRoleChange changes);

}
