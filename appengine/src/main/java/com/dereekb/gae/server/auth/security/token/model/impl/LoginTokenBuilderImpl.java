package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenBuilder;
import com.dereekb.gae.server.datastore.Getter;

/**
 * Default {@link LoginTokenBuilder} implementation.
 *
 * @author dereekb
 */
public final class LoginTokenBuilderImpl extends ExtendedAbstractLoginTokenBuilder<LoginTokenImpl> {

	public LoginTokenBuilderImpl(Getter<Login> loginGetter) throws IllegalArgumentException {
		super(loginGetter);
	}

	// MARK: AbstractLoginTokenBuilder
	@Override
	protected LoginTokenImpl newLoginToken() {
		return new LoginTokenImpl();
	}

	@Override
	public String toString() {
		return "LoginTokenBuilderImpl []";
	}

}
