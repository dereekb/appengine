package com.dereekb.gae.server.auth.security.system.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenFactory;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoder;
import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.server.auth.security.token.parameter.AuthenticationParameterBuilder;
import com.dereekb.gae.server.auth.security.token.parameter.impl.AuthenticationParameterServiceImpl;
import com.dereekb.gae.utilities.data.StringUtility;
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

	// 30 day expiration, more than long enough for the taskqueue.
	public static final Long DEFAULT_EXPIRATION_TIME = 30 * 24 * 60 * 60 * 1000L;
	public static final String DEFAULT_SUBJECT = "SYSTEM";
	public static final Long DEFAULT_ROLES = 0L;

	@Deprecated
	private AuthenticationParameterBuilder authParameterBuilder = AuthenticationParameterServiceImpl.SINGLETON;

	private LoginPointerType pointerType = LoginPointerType.SYSTEM;

	private Long expirationTime = DEFAULT_EXPIRATION_TIME;
	private String subject = DEFAULT_SUBJECT;

	private String app;
	private Long roles;

	private LoginTokenEncoder<LoginToken> encoder;

	public SystemLoginTokenFactoryImpl(LoginTokenEncoder<LoginToken> encoder) {
		this(encoder, DEFAULT_ROLES);
	}

	public SystemLoginTokenFactoryImpl(LoginTokenEncoder<LoginToken> encoder, Long roles) {
		this.setEncoder(encoder);
		this.setRoles(roles);
	}

	public SystemLoginTokenFactoryImpl(String app, LoginTokenEncoder<LoginToken> encoder, Long roles) {
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

	public String getApp() {
		return this.app;
	}

	public void setApp(String app) {
		if (StringUtility.isEmptyString(app)) {
			throw new IllegalArgumentException("App cannot be null.");
		}

		this.app = app;
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

	public LoginTokenEncoder<LoginToken> getEncoder() {
		return this.encoder;
	}

	public void setEncoder(LoginTokenEncoder<LoginToken> encoder) {
		if (encoder == null) {
			throw new IllegalArgumentException("Encoder cannot be null.");
		}

		this.encoder = encoder;
	}

	// MARK: SystemLoginTokenFactory
	@Override
	public SignedEncodedLoginToken makeSystemToken() throws FactoryMakeFailureException {
		LoginToken token = this.makeLoginToken();
		String encodedToken = this.encoder.encodeLoginToken(token);

		// TODO: ...

	}

	@Deprecated
	@Override
	public KeyedEncodedParameter makeTokenHeader() throws FactoryMakeFailureException {
		String tokenString = this.makeEncodedToken();
		return this.authParameterBuilder.buildAuthenticationParameter(tokenString);
	}

	@Deprecated
	@Override
	public String makeEncodedToken() throws FactoryMakeFailureException {
		LoginToken token = this.makeLoginToken();
		return this.encoder.encodeLoginToken(token);
	}

	public LoginTokenImpl makeLoginToken() throws FactoryMakeFailureException {
		LoginTokenImpl token = new LoginTokenImpl(this.pointerType);

		token.setApp(this.getApp());
		token.setRoles(this.getRoles());
		token.setRefreshAllowed(false);
		token.setExpiration(this.expirationTime);

		return token;
	}


}
