package com.thevisitcompany.gae.deprecated.model.users.login.functions;

import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.model.crud.function.delegate.CreateFunctionDelegate;
import com.thevisitcompany.gae.model.crud.function.delegate.UpdateFunctionDelegate;

public class LoginFunctionsDelegate
        implements CreateFunctionDelegate<Login>, UpdateFunctionDelegate<Login> {

	@Override
	public boolean update(Login source,
	                      Login context) {

		context.setSettings(source.getSettings());
		return true;
	}

	@Override
	public Login create(Login source) {
		Login login = new Login();

		login.setPermissions(source.getPermissions());
		login.setEmail(source.getEmail());

		return login;
	}

}
