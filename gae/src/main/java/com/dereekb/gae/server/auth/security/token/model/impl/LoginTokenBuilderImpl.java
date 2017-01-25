package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
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

	// 4 hour expiration
	private static final Long DEFAULT_EXPIRATION_TIME = 4 * 60 * 60 * 1000L;

	private Long expirationTime = DEFAULT_EXPIRATION_TIME;

	private Getter<Login> loginGetter;

	public LoginTokenBuilderImpl(Getter<Login> loginGetter) throws IllegalArgumentException {
		this.setLoginGetter(loginGetter);
	}

	public Long getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Long expirationTime) {
		if (expirationTime == null || expirationTime < 0L) {
			throw new IllegalArgumentException("ExpirationTime cannot be null or less than 0.");
		}

		this.expirationTime = expirationTime;
	}

	public Getter<Login> getLoginGetter() {
		return this.loginGetter;
	}

	public void setLoginGetter(Getter<Login> loginGetter) throws IllegalArgumentException {
		if (loginGetter == null) {
			throw new IllegalArgumentException("LoginGetter cannot be null.");
		}

		this.loginGetter = loginGetter;
	}

	// MARK: LoginTokenBuilder
	@Override
	public LoginToken buildAnonymousLoginToken(String anonymousId) {
		LoginTokenImpl loginToken = this.makeLoginToken();

		loginToken.setSubject(anonymousId);
		loginToken.setAnonymous(true);
		loginToken.setPointerType(LoginPointerType.ANONYMOUS);

		return loginToken;
	}

	@Override
	public LoginTokenImpl buildLoginToken(LoginPointer pointer) {
		LoginTokenImpl loginToken = this.makeLoginToken();

		ModelKey loginKey = pointer.getLoginModelKey();
		Long roles = null;
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
		loginToken.setPointerType(pointer.getLoginPointerType());

		return loginToken;
	}

	protected LoginTokenImpl makeLoginToken() {
		LoginTokenImpl loginToken = new LoginTokenImpl();

		Date issued = new Date();

		loginToken.setIssued(issued);
		loginToken.setExpiration(this.expirationTime);

		return loginToken;
	}

	@Override
	public String toString() {
		return "LoginTokenBuilderImpl [expirationTime=" + this.expirationTime + ", loginGetter=" + this.loginGetter
		        + "]";
	}

}
