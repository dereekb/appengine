package com.thevisitcompany.gae.deprecated.authentication.registration;

import com.thevisitcompany.gae.deprecated.model.users.login.Login;


/**
 * Function to run after a login has been generated.
 * @author dereekb
 */
public interface RegistrationHandlerFunction {

	public void modifyRegisteredLogin(Login login);
	
}
