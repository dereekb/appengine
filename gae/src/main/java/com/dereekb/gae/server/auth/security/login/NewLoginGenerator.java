package com.dereekb.gae.server.auth.security.login;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Used for generating new {@link Login} objects.
 *
 * @author dereekb
 *
 */
public interface NewLoginGenerator {

	/**
	 * Creates a new {@link Login}, using data from the {@link LoginPointer}.
	 * <p>
	 * The login and pointer are not necessarily linked after this executes.
	 *
	 * @param pointer
	 *            {@link LoginPointer}. Can be {@code null} depending on
	 *            implementation.
	 * @return {@link Login}.
	 */
	public Login makeLogin(LoginPointer pointer);

}
