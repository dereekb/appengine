package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenBuilder;
import com.googlecode.objectify.Key;

/**
 * {@link LoginTokenBuilder} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenBuilderImpl
        implements LoginTokenBuilder {

	// 1 hour expiration
	private static final Long DEFAULT_EXPIRATION_TIME = 60 * 60 * 1000L;

	private Long expirationTime = DEFAULT_EXPIRATION_TIME;

	public Long getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Long expirationTime) {
		this.expirationTime = expirationTime;
	}

	// MARK: LoginTokenBuilder
	@Override
	public LoginTokenImpl buildLoginToken(LoginPointer pointer) {
		LoginTokenImpl loginToken = new LoginTokenImpl();

		Key<Login> loginKey = pointer.getLogin();
		Long login = null;

		if (loginKey != null) {
			login = loginKey.getId();
		}

		String pointerId = pointer.getIdentifier();

		loginToken.setLogin(login);
		loginToken.setLoginPointer(pointerId);

		Date issued = new Date();
		Date expiration = this.getExpirationDate();

		loginToken.setIssued(issued);
		loginToken.setExpiration(expiration);

		return loginToken;
	}

	public Date getExpirationDate() {
		return new Date(System.currentTimeMillis() + this.expirationTime);
	}

	@Override
	public String toString() {
		return "LoginTokenBuilderImpl [expirationTime=" + this.expirationTime + "]";
	}

}
