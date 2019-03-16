package com.dereekb.gae.server.auth.old.security.authentication;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Tuple of login information.
 *
 * @author dereekb
 */
@Deprecated
public interface LoginTuple {

	/**
	 * @return the system used to login.
	 */
	public String getLoginSystem();

	/**
	 * @return the current {@link Login}.
	 */
	public Login getLogin();

	/**
	 * @return the current {@link LoginPointer} used to login.
	 */
	public LoginPointer getLoginPointer();

}
