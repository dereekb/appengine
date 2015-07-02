package com.thevisitcompany.gae.deprecated.authentication.login.support;

import com.thevisitcompany.gae.deprecated.model.users.login.Login;

/**
 * Basic helper class that carries a login, and can be chained with other login-dependent models.F
 * 
 * @author dereekb
 */
public abstract class LoginTrackingModel
        implements LoginDependent {

	protected Login login;

	protected LoginDependent loginChainItem;

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;

		if (loginChainItem != null) {
			loginChainItem.setLogin(login);
		}
	}

	public boolean hasLogin() {
		return (this.login != null);
	}

}
