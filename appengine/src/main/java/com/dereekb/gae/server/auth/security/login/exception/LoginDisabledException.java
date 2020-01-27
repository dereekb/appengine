package com.dereekb.gae.server.auth.security.login.exception;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Thrown when the login is marked disabled.
 *
 * @author dereekb
 *
 */
public class LoginDisabledException extends LoginAuthenticationException {

	private static final long serialVersionUID = 1L;

	private Login login;
	private LoginPointer loginPointer;

	public LoginDisabledException(LoginPointer pointer) {
		this();
		this.setLoginPointer(pointer);
	}

	public LoginDisabledException() {
		super();
	}

	public LoginDisabledException(String message, String encodedData) {
		super(message, encodedData);
	}

	public LoginDisabledException(String message) {
		super(message);
	}

	public Login getLogin() {
		return this.login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public LoginPointer getLoginPointer() {
		return this.loginPointer;
	}

	public void setLoginPointer(LoginPointer loginPointer) {
		this.loginPointer = loginPointer;
	}

}
