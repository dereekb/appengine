package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.misc.loader.LoginUserLoader;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenBuilderOptions;
import com.dereekb.gae.server.datastore.Getter;

/**
 * Abstract extension of {@link ExtendedAbstractLoginTokenBuilder} that also
 * loads the user associated with the {@link Login} and initializes the token.
 *
 * @author dereekb
 *
 * @param <U>
 *            user type
 * @param <T>
 *            token type
 */
public abstract class AbstractUserLoginTokenBuilder<U, T extends LoginTokenImpl> extends ExtendedAbstractLoginTokenBuilder<T> {

	private LoginUserLoader<U> userLoader;

	public AbstractUserLoginTokenBuilder(Getter<Login> loginGetter, LoginUserLoader<U> userLoader)
	        throws IllegalArgumentException {
		super(loginGetter);
		this.setUserLoader(userLoader);
	}

	public LoginUserLoader<U> getUserLoader() {
		return this.userLoader;
	}

	public void setUserLoader(LoginUserLoader<U> userLoader) {
		if (userLoader == null) {
			throw new IllegalArgumentException("userLoader cannot be null.");
		}

		this.userLoader = userLoader;
	}

	// MARK: Override
	@Override
	public T buildAnonymousLoginToken(String anonymousId) {
		T loginToken = this.makeLoginToken();

		loginToken.setSubject(anonymousId);
		loginToken.setPointerType(LoginPointerType.ANONYMOUS);

		return loginToken;
	}

	@Override
	public T buildLoginToken(LoginPointer pointer,
	                         boolean refreshAllowed) {
		return this.buildLoginToken(pointer, new LoginTokenBuilderOptionsImpl(refreshAllowed));
	}

	@Override
	public T buildLoginToken(LoginPointer pointer,
	                         LoginTokenBuilderOptions options) {
		T loginToken = this.makeLoginToken();
		loginToken.setRefreshAllowed(options.getRefreshAllowed() || false);

		this.initLoginToken(loginToken, pointer, options);

		return loginToken;
	}

	@Override
	protected void initLoginTokenWithLogin(T loginToken,
	                                       Login login,
	                                       LoginTokenBuilderOptions options) {
		super.initLoginTokenWithLogin(loginToken, login, options);

		if (login != null) {
			try {
				U user = this.userLoader.loadUserForLogin(login);
				this.initLoginTokenWithUser(loginToken, user, options);
			} catch (UnavailableModelException e) {
				// Do nothing?
			}
		}
	}

	protected abstract void initLoginTokenWithUser(T loginToken,
	                                               U user,
	                                               LoginTokenBuilderOptions options);

	@Override
	protected abstract T newLoginToken();

}
