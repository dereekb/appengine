package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenBuilder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenBuilderOptions;

/**
 * Abstract {@link LoginTokenBuilder} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            token type
 */
public abstract class AbstractLoginTokenBuilder<T extends LoginTokenImpl>
        implements LoginTokenBuilder<T> {

	// 2 hour expiration
	private static final Long DEFAULT_EXPIRATION_TIME = 2 * 60 * 60 * 1000L;

	private Long expirationTime = DEFAULT_EXPIRATION_TIME;

	public AbstractLoginTokenBuilder() {
		super();
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

	// MARK: LoginTokenBuilder
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
	public T buildLoginToken(LoginPointer pointer,
	                         Login login,
	                         boolean refreshAllowed) {
		return this.buildLoginToken(pointer, login, new LoginTokenBuilderOptionsImpl(refreshAllowed));
	}

	@Override
	public T buildLoginToken(LoginPointer pointer,
	                         Login login,
	                         LoginTokenBuilderOptions options) {
		T loginToken = this.makeLoginToken();
		loginToken.setRefreshAllowed(options.getRefreshAllowed() || false);

		this.initLoginToken(loginToken, login, pointer, options);

		return loginToken;
	}

	protected abstract void initLoginToken(T loginToken,
	                                       LoginPointer pointer,
	                                       LoginTokenBuilderOptions options);

	protected abstract void initLoginToken(T loginToken,
	                                       Login login,
	                                       LoginPointer pointer,
	                                       LoginTokenBuilderOptions options);

	protected T makeLoginToken() {
		T loginToken = this.newLoginToken();

		Date issued = new Date();

		loginToken.setIssued(issued);
		loginToken.setExpiration(this.expirationTime);

		return loginToken;
	}

	protected abstract T newLoginToken();

}
