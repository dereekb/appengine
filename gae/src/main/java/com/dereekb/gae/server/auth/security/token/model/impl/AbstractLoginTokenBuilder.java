package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenBuilder;

/**
 * 
 * @author dereekb
 *
 * @param <T>
 *            token type
 */
public abstract class AbstractLoginTokenBuilder<T extends LoginTokenImpl>
        implements LoginTokenBuilder {

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
		T loginToken = this.makeLoginToken();
		loginToken.setRefreshAllowed(refreshAllowed);

		this.initLoginToken(loginToken, pointer);

		return loginToken;
	}

	protected abstract void initLoginToken(T loginToken,
	                                       LoginPointer pointer);

	protected T makeLoginToken() {
		T loginToken = this.newLoginToken();

		Date issued = new Date();

		loginToken.setIssued(issued);
		loginToken.setExpiration(this.expirationTime);

		return loginToken;
	}

	protected abstract T newLoginToken();

}
