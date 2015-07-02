package com.thevisitcompany.gae.deprecated.model.users.login.utility;

import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSource;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.deprecated.model.users.login.LoginFunctionsService;

public class LoginUtility {

	private LoginFunctionsService loginService;
	private LoginSource loginSource;

	public LoginUtility() {}

	/**
	 * 
	 * @return The current login, or null if none is available.
	 */
	public Login getCurrentLogin() {
		Login login = loginSource.getLogin();
		return login;
	}

	public LoginFunctionsService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginFunctionsService loginService) {
		this.loginService = loginService;
	}

	public LoginSource getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(LoginSource loginSource) {
		this.loginSource = loginSource;
	}

	// TODO: Set default account

	// TODO: Add Permissions

	// TODO: Remove Permissions

	// TODO: Enable Account

	// TODO: Disable Account

}
