package com.dereekb.gae.server.auth.security.system.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenRequest;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenResponse;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenService;
import com.dereekb.gae.server.auth.security.system.exception.NoAppSpecifiedSystemLoginTokenServiceException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;

/**
 * {@link SystemLoginTokenService} implementation.
 * <p>
 * Used only by a local login system.
 *
 * @author dereekb
 *
 */
public class SystemLoginTokenServiceImpl
        implements SystemLoginTokenService {

	// 30 day expiration, more than long enough for the taskqueue.
	public static final Long DEFAULT_EXPIRATION_TIME = 30 * 24 * 60 * 60 * 1000L;
	public static final String DEFAULT_SUBJECT = "SYSTEM";
	public static final Long DEFAULT_ROLES = 0L;

	private static final LoginPointerType SYSTEM_POINTER_TYPE = LoginPointerType.SYSTEM;

	private Long expirationTime = DEFAULT_EXPIRATION_TIME;
	private String subject = DEFAULT_SUBJECT;

	/**
	 * Mask of all allowed roles.
	 */
	private Long systemEncodedRoles;

	private LoginTokenEncoderDecoder<LoginToken> dencoder;

	public SystemLoginTokenServiceImpl(LoginTokenEncoderDecoder<LoginToken> dencoder) {
		this(DEFAULT_ROLES, dencoder);
	}

	public SystemLoginTokenServiceImpl(Long systemEncodedRoles, LoginTokenEncoderDecoder<LoginToken> dencoder) {
		this.setSystemEncodedRoles(systemEncodedRoles);
		this.setDencoder(dencoder);
	}

	public Long getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Long expirationTime) {
		if (expirationTime == null) {
			throw new IllegalArgumentException("expirationTime cannot be null.");
		}

		this.expirationTime = expirationTime;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		if (subject == null) {
			throw new IllegalArgumentException("subject cannot be null.");
		}

		this.subject = subject;
	}

	public Long getSystemEncodedRoles() {
		return this.systemEncodedRoles;
	}

	public void setSystemEncodedRoles(Long systemEncodedRoles) {
		if (systemEncodedRoles == null) {
			throw new IllegalArgumentException("systemEncodedRoles cannot be null.");
		}

		this.systemEncodedRoles = systemEncodedRoles;
	}

	public LoginTokenEncoderDecoder<LoginToken> getDencoder() {
		return this.dencoder;
	}

	public void setDencoder(LoginTokenEncoderDecoder<LoginToken> dencoder) {
		if (dencoder == null) {
			throw new IllegalArgumentException("dencoder cannot be null.");
		}

		this.dencoder = dencoder;
	}

	// MARK: SystemLoginTokenService
	@Override
	public SystemLoginTokenResponse makeSystemToken(SystemLoginTokenRequest request) {

		LoginTokenImpl templateToken = new LoginTokenImpl(SYSTEM_POINTER_TYPE);

		String appId = request.getAppId();
		Long roles = this.maskRoles(request.getRoles());
		Long expiresIn = ValueUtility.defaultTo(request.getExpiresIn(), this.expirationTime);

		if (StringUtility.isEmptyString(appId)) {
			throw new NoAppSpecifiedSystemLoginTokenServiceException();
		}

		templateToken.setApp(appId);
		templateToken.setRoles(roles);
		templateToken.setExpiration(expiresIn);
		templateToken.setRefreshAllowed(false);

		LoginToken token = this.dencoder.makeToken(templateToken);
		return new SystemTokenResponseImpl(token);
	}

	public Long maskRoles(Long roles) {
		Long systemRoles = this.getSystemEncodedRoles();

		if (roles == null) {
			return systemRoles;	// No roles if none provided.
		} else {
			LongBitContainer mask = new LongBitContainer(systemRoles);
			return mask.and(roles);
		}
	}

	// MARK: Internal
	protected class SystemTokenResponseImpl
	        implements SystemLoginTokenResponse {

		private final LoginToken loginToken;
		private transient String encodedLoginToken;

		public SystemTokenResponseImpl(LoginToken loginToken) {
			super();
			this.loginToken = loginToken;
		}

		// MARK: SystemTokenResponse
		@Override
		public LoginToken getLoginToken() {
			return this.loginToken;
		}

		@Override
		public String getEncodedLoginToken() {
			if (this.encodedLoginToken == null) {
				this.encodedLoginToken = SystemLoginTokenServiceImpl.this.dencoder.encodeLoginToken(this.loginToken);
			}

			return this.encodedLoginToken;
		}

		@Override
		public String toString() {
			return "SystemTokenResponseImpl [loginToken=" + this.loginToken + "]";
		}

	}

}
