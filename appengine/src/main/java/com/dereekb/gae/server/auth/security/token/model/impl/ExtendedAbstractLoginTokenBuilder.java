package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract extension of {@link AbstractLoginTokenBuilder} that will load the
 * {@link Login} and initialize the {@link LoginToken} with it.
 *
 * @author dereekb
 *
 * @param <T>
 *            token type
 *
 * @see AbstractUserLoginTokenBuilder for extension that also include user data.
 */
public abstract class ExtendedAbstractLoginTokenBuilder<T extends LoginTokenImpl> extends AbstractLoginTokenBuilder<T> {

	private Getter<Login> loginGetter;

	public ExtendedAbstractLoginTokenBuilder(Getter<Login> loginGetter) throws IllegalArgumentException {
		this.setLoginGetter(loginGetter);
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
	protected void initLoginToken(T loginToken,
	                              LoginPointer pointer) {

		String pointerId = pointer.getIdentifier();
		loginToken.setLoginPointer(pointerId);
		loginToken.setPointerType(pointer.getLoginPointerType());

		ModelKey loginKey = pointer.getLoginOwnerKey();
		this.initLoginTokenWithLogin(loginToken, loginKey);
	}

	protected void initLoginTokenWithLogin(T loginToken,
	                                       ModelKey loginKey) {
		if (loginKey != null) {
			Login login = this.loginGetter.get(loginKey);

			if (login != null) {
				this.initLoginTokenWithLogin(loginToken, login);
			}
		}
	}

	protected void initLoginTokenWithLogin(T loginToken,
	                                       Login login) {
		loginToken.setLogin(login.getIdentifier());

		Long roles = login.getRoles();
		loginToken.setRoles(roles);
	}

}
