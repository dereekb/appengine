package com.dereekb.gae.server.auth.old.security.authentication;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.old.security.registration.creator.LoginCreator;

/**
 * Used by {@link LoginCreator} to return the created login and key.
 *
 * @author dereekb
 *
 */
public final class LoginTupleImpl
        implements LoginTuple {

	/**
	 * The Login System this pair was created under.
	 */
	private final String loginSystem;

	/**
	 * The newly created {@link Login}.
	 */
	private final Login login;

	/**
	 * The newly created {@link LoginPointer}.
	 */
	private final LoginPointer loginPointer;

	public LoginTupleImpl(String loginSystem, Login login, LoginPointer loginPointer) throws IllegalArgumentException {
		if (loginSystem == null || login == null || loginPointer == null) {
			throw new IllegalArgumentException("Failed to create tuple. All arguments must not be null.");
		}

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
