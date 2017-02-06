package com.dereekb.gae.web.api.auth.controller.password.impl;

import com.dereekb.gae.server.auth.security.login.password.PasswordLoginPair;

/**
 * {@link PasswordLoginPair} implementation.
 *
 * @author dereekb
 *
 */
public class PasswordLoginPairImpl
        implements PasswordLoginPair {

	private String username;
	private String password;

	public PasswordLoginPairImpl() {}

	public PasswordLoginPairImpl(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) throws IllegalArgumentException {
		if (username == null) {
			throw new IllegalArgumentException("Username cannot be null.");
		}

		this.username = username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) throws IllegalArgumentException {
		if (password == null) {
			throw new IllegalArgumentException("Password cannot be null.");
		}

		this.password = password;
	}

	@Override
	public String toString() {
		return "PasswordLoginPairImpl [username=" + this.username + ", password=" + this.password + "]";
	}

}
