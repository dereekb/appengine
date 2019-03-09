package com.dereekb.gae.server.auth.security.system.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenFactory;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoder;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.server.auth.security.token.parameter.AuthenticationParameterBuilder;
import com.dereekb.gae.server.auth.security.token.parameter.impl.AuthenticationParameterServiceImpl;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

/**
 * {@link SystemLoginTokenFactory} implementation.
 * 
 * @author dereekb
 *
 */
public class SystemLoginTokenFactoryImpl
        implements SystemLoginTokenFactory {

	// 20 minute expiration, long enough for the taskqueue
	public static final Long DEFAULT_EXPIRATION_TIME = 20 * 60 * 1000L;
	public static final String DEFAULT_SUBJECT = "SYSTEM TOKEN";
	public static final Long DEFAULT_ROLES = 0L;

	private AuthenticationParameterBuilder authParameterBuilder = AuthenticationParameterServiceImpl.SINGLETON;

	private LoginPointerType pointerType = LoginPointerType.SYSTEM;

	private Long expirationTime = DEFAULT_EXPIRATION_TIME;
	private String subject = DEFAULT_SUBJECT;
	private Long roles;

	private LoginTokenEncoder encoder;

	public SystemLoginTokenFactoryImpl(LoginTokenEncoder encoder) {
		this(encoder, DEFAULT_ROLES);
	}

	public SystemLoginTokenFactoryImpl(LoginTokenEncoder encoder, Long roles) {
		this.setEncoder(encoder);
		this.setRoles(roles);
	}

	public AuthenticationParameterBuilder getAuthParameterBuilder() {
		return this.authParameterBuilder;
	}

	public void setAuthParameterBuilder(AuthenticationParameterBuilder authParameterBuilder) {
		if (authParameterBuilder == null) {
			throw new IllegalArgumentException("AuthParameterBuilder cannot be null.");
		}

		this.authParameterBuilder = authParameterBuilder;
	}

	public LoginPointerType getPointerType() {
		return this.pointerType;
	}

	public void setPointerType(LoginPointerType pointerType) throws IllegalArgumentException {
		if (pointerType == null) {
			throw new IllegalArgumentException("PointerType cannot be null.");
		}

		this.pointerType = pointerType;
	}

	public Long getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Long expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getRoles() {
		return this.roles;
	}

	public void setRoles(Long roles) {
		if (roles == null) {
			throw new IllegalArgumentException("Roles cannot be null.");
		}

		this.roles = roles;
	}

	public LoginTokenEncoder getEncoder() {
		return this.encoder;
	}

	public void setEncoder(LoginTokenEncoder encoder) {
		if (encoder == null) {
			throw new IllegalArgumentException("Encoder cannot be null.");
		}

		this.encoder = encoder;
	}

	// MARK: SystemLoginTokenFactory
	@Override
	public KeyedEncodedParameter makeTokenHeader() throws FactoryMakeFailureException {
		String tokenString = this.makeTokenString();
		return this.authParameterBuilder.buildAuthenticationParameter(tokenString);
	}

	@Override
	public String makeTokenString() throws FactoryMakeFailureException {
		LoginToken token = this.makeLoginToken();
		return this.encoder.encodeLoginToken(token);
	}

	public LoginTokenImpl makeLoginToken() throws FactoryMakeFailureException {
		LoginTokenImpl token = new LoginTokenImpl(this.pointerType);

		token.setRoles(this.roles);
		token.setRefreshAllowed(false);
		token.setExpiration(this.expirationTime);

		return token;
	}

}
