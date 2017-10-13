package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenBuilder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;

/**
 * {@link LoginTokenService} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenServiceImpl<T extends LoginToken>
        implements LoginTokenService<T> {

	private LoginTokenBuilder<T> builder;
	private LoginTokenEncoderDecoder<T> dencoder;

	public LoginTokenServiceImpl(LoginTokenBuilder<T> builder, LoginTokenEncoderDecoder<T> dencoder) {
		super();
		this.setBuilder(builder);
		this.setDencoder(dencoder);
	}

	public LoginTokenBuilder<T> getBuilder() {
		return this.builder;
	}

	public void setBuilder(LoginTokenBuilder<T> builder) {
		if (builder == null) {
			throw new IllegalArgumentException("builder cannot be null.");
		}

		this.builder = builder;
	}

	public LoginTokenEncoderDecoder<T> getDencoder() {
		return this.dencoder;
	}

	public void setDencoder(LoginTokenEncoderDecoder<T> dencoder) {
		if (dencoder == null) {
			throw new IllegalArgumentException("dencoder cannot be null.");
		}

		this.dencoder = dencoder;
	}

	// MARK: LoginTokenService
	@Override
	public String encodeAnonymousLoginToken(String anonymousId) {
		T token = this.buildAnonymousLoginToken(anonymousId);
		return this.encodeLoginToken(token);
	}

	@Override
	public T buildAnonymousLoginToken(String anonymousId) {
		return this.builder.buildAnonymousLoginToken(anonymousId);
	}

	@Override
	public String encodeLoginToken(LoginPointer pointer,
	                               boolean refreshAllowed) {
		T token = this.buildLoginToken(pointer, refreshAllowed);
		return this.encodeLoginToken(token);
	}

	@Override
	public T buildLoginToken(LoginPointer pointer,
	                         boolean refreshAllowed) {
		return this.builder.buildLoginToken(pointer, refreshAllowed);
	}

	@Override
	public String encodeLoginToken(T loginToken) {
		return this.dencoder.encodeLoginToken(loginToken);
	}

	@Override
	public DecodedLoginToken<T> decodeLoginToken(String token)
	        throws TokenExpiredException,
	            TokenUnauthorizedException {
		return this.dencoder.decodeLoginToken(token);
	}

	@Override
	public String toString() {
		return "LoginTokenServiceImpl [builder=" + this.builder + "]";
	}

}
