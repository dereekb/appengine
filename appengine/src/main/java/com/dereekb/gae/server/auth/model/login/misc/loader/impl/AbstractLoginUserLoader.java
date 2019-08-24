package com.dereekb.gae.server.auth.model.login.misc.loader.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.misc.loader.LoginUserLoader;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract {@link LoginUserLoader} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractLoginUserLoader<T>
        implements LoginUserLoader<T> {

	// MARK: LoginUserLoader
	@Override
	public final T loadUserForLogin(Login login) {
		if (login == null) {
			throw new IllegalArgumentException("Login cannot be null.");
		}

		return this.loadUserForLogin(login.getModelKey());
	}

	@Override
	public final T loadUserForLogin(ModelKey loginKey) {
		if (loginKey == null) {
			throw new IllegalArgumentException("Login Key cannot be null.");
		}

		return this.findUserForLogin(loginKey);
	}

	protected abstract T findUserForLogin(ModelKey loginKey);

}
