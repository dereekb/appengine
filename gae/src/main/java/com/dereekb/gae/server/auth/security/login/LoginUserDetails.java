package com.dereekb.gae.server.auth.security.login;

import org.springframework.security.core.userdetails.UserDetails;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * {@link UserDetails} extension for {@link Login} information.
 *
 * @author dereekb
 *
 */
public interface LoginUserDetails
        extends UserDetails {

	/**
	 * Returns the current {@link Login}.
	 *
	 * @return {@link Login}. May be {@code null}.
	 */
	public Login getLogin();

	/**
	 * Returns the {@link LoginPointer} associated with this instance.
	 *
	 * @return {@link LoginPointer}. May be {@code null}.
	 */
	public LoginPointer getLoginPointer();

}
