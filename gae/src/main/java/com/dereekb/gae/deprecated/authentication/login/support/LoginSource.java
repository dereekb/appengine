package com.thevisitcompany.gae.deprecated.authentication.login.support;

import com.thevisitcompany.gae.deprecated.model.users.login.Login;

/**
 * Login Delegate that returns a login.
 * @author dereekb
 */
public interface LoginSource {

	/**
	 * Returns the login.
	 * @return
	 */
	public Login getLogin();
	
}
