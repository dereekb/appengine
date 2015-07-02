package com.dereekb.gae.server.auth.security.authentication;

import org.springframework.security.core.Authentication;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

public interface LoginAuthentication
        extends Authentication {

	/**
	 * @return the {@link Login} for this authentication.
	 */
	public Login getLogin();

	/**
	 * @return the name of the system used for logging in.
	 */
	public String getLoginSystem();

	/**
	 * @return the login pointer used to login.
	 */
	public LoginPointer getLoginPointer();

}
