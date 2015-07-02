package com.thevisitcompany.gae.deprecated.authentication.login.support;

import com.thevisitcompany.gae.deprecated.model.users.login.Login;

public class AbstractLoginSourceDependent
        implements LoginSourceDependent {

	protected LoginSource delegate;

	protected Login getLogin() {
		return delegate.getLogin();
	}

	public LoginSource getLoginSource() {
		return this.delegate;
	}

	public void setLoginSource(LoginSource delegate) {
		this.setLoginSource(delegate);
	}

}
