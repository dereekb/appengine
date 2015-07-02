package com.thevisitcompany.gae.deprecated.authentication.login.filters;

import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginDependent;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.utilities.filters.AbstractFilter;

public abstract class LoginDependentFilter<T> extends AbstractFilter<T>
        implements LoginDependent {

	private Login login;

	@Override
	public Login getLogin() {
		return this.login;
	}

	@Override
	public void setLogin(Login login) {
		this.login = login;
	}

}
