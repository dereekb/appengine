package com.dereekb.gae.server.auth.security.login;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.impl.NewLoginGeneratorImpl;

/**
 * {@link NewLoginGeneratorImpl} delegate.
 *
 * @author dereekb
 *
 */
public interface NewLoginGeneratorDelegate {

	/**
	 * Creates a new {@link Login}, using data from the {@link LoginPointer}.
	 * <p>
	 * The login and pointer are not necessarily linked after this executes.
	 *
	 * @param pointer
	 *            {@link LoginPointer}. Can be {@code null} depending on
	 *            implementation.
	 * @return {@link Login} without an identifier.
	 */
	public Login makeNewLogin(LoginPointer pointer);

}
