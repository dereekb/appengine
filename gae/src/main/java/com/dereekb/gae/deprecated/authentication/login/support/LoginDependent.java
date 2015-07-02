package com.thevisitcompany.gae.deprecated.authentication.login.support;

import com.thevisitcompany.gae.deprecated.model.users.login.Login;

/**
 * A Login dependent item that requires a login.
 * 
 * @author dereekb
 */
public interface LoginDependent extends LoginSource {

	public void setLogin(Login login);
	
}
