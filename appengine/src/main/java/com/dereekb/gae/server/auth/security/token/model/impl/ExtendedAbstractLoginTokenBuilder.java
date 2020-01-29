package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenBuilderOptions;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;

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
	                              LoginPointer pointer,
	                              LoginTokenBuilderOptions options) {

		ModelKey loginKey = pointer.getLoginOwnerKey();
		Login login = null;

		if (loginKey != null) {
			login = this.loginGetter.get(loginKey);
		}

		this.initLoginToken(loginToken, login, pointer, options);
	}

	@Override
	protected void initLoginToken(T loginToken,
	                              Login login,
	                              LoginPointer pointer,
	                              LoginTokenBuilderOptions options) {

		String pointerId = pointer.getIdentifier();
		loginToken.setLoginPointer(pointerId);
		loginToken.setPointerType(pointer.getLoginPointerType());

		this.initLoginTokenWithLogin(loginToken, login, options);
	}

	/**
	 * @deprecated Use {@link #initLoginToken} instead.
	 *
	 * @param loginToken
	 * @param loginKey
	 */
	@Deprecated
	protected void initLoginTokenWithLogin(T loginToken,
	                                       ModelKey loginKey) {
		if (loginKey != null) {
			Login login = this.loginGetter.get(loginKey);

			if (login != null) {
				this.initLoginTokenWithLogin(loginToken, login, new LoginTokenBuilderOptionsImpl());
			}
		}
	}

	protected void initLoginTokenWithLogin(T loginToken,
	                                       Login login,
	                                       LoginTokenBuilderOptions options) {
		if (login != null) {
			loginToken.setLogin(login.getIdentifier());

			Long loginRoles = login.getRoles();
			Long rolesMask = options.getRolesMask();

			Long roles = maskRoles(loginRoles, rolesMask);
			loginToken.setRoles(roles);
		}
	}

	protected Long maskRoles(Long roles,
	                         Long mask) {
		if (roles == null) {
			return null;
		} else if (mask == null) {
			return roles;
		} else {
			LongBitContainer container = new LongBitContainer(mask);
			return container.and(roles);
		}
	}

}
