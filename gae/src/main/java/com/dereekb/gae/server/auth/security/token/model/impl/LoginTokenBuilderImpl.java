package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;
import java.util.Set;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenBuilder;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

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

	private Getter<Login> loginGetter;

	public LoginTokenBuilderImpl(Getter<Login> loginGetter) {
		this.setLoginGetter(loginGetter);
	}

	public Long getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Long expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Getter<Login> getLoginGetter() {
		return this.loginGetter;
	}

	public void setLoginGetter(Getter<Login> loginGetter) {
		this.loginGetter = loginGetter;
	}

	// MARK: LoginTokenBuilder
	@Override
	public LoginTokenImpl buildLoginToken(LoginPointer pointer) {
		LoginTokenImpl loginToken = new LoginTokenImpl();

		ModelKey loginKey = pointer.getLoginModelKey();
		Set<Integer> roles = null;
		Long loginId = null;

		if (loginKey != null) {
			loginId = loginKey.getId();

			Login login = this.loginGetter.get(loginKey);

			if (login != null) {
				roles = login.getRoles();
			}
		}

		String pointerId = pointer.getIdentifier();

		loginToken.setLogin(loginId);
		loginToken.setLoginPointer(pointerId);
		loginToken.setRoles(roles);

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
		return "LoginTokenBuilderImpl [expirationTime=" + this.expirationTime + ", loginGetter=" + this.loginGetter
		        + "]";
	}

}
