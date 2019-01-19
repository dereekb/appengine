package com.dereekb.gae.server.auth.old.security.filter.exception;

import com.dereekb.gae.server.auth.deprecated.old.security.authentication.LoginTuple;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Thrown to signify a new login has been created, and should be treated
 * differently.
 *
 * @author dereekb
 */
@Deprecated
public class NewLoginAuthenticationException extends RuntimeException
        implements LoginTuple {

	private static final long serialVersionUID = 1L;

	private final String loginSystem;

	private final Login login;

	private final LoginPointer loginPointer;

	public NewLoginAuthenticationException(LoginTuple loginTuple) {
		this.loginSystem = loginTuple.getLoginSystem();
		this.login = loginTuple.getLogin();
		this.loginPointer = loginTuple.getLoginPointer();
	}

	public NewLoginAuthenticationException(String loginSystem, Login login, LoginPointer loginPointer) {
		this.loginSystem = loginSystem;
		this.login = login;
		this.loginPointer = loginPointer;
	}

	@Override
    public String getLoginSystem() {
		return this.loginSystem;
	}

	@Override
    public Login getLogin() {
		return this.login;
	}

	@Override
    public LoginPointer getLoginPointer() {
		return this.loginPointer;
	}

}
