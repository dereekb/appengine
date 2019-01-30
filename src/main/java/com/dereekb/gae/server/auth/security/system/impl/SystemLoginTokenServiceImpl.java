package com.dereekb.gae.server.auth.security.system.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenRequest;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenResponse;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenService;
import com.dereekb.gae.server.auth.security.system.exception.NoAppSpecifiedSystemLoginTokenServiceException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoder;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;

/**
 * {@link SystemLoginTokenService} implementation.
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
	private Long rolesMask;

	private LoginTokenEncoder<LoginToken> encoder;

	public SystemLoginTokenServiceImpl(LoginTokenEncoder<LoginToken> encoder) {
		this(DEFAULT_ROLES, encoder);
	}

	public SystemLoginTokenServiceImpl(Long rolesMask, LoginTokenEncoder<LoginToken> encoder) {
		this.setRolesMask(rolesMask);
		this.setEncoder(encoder);
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

	public Long getRolesMask() {
		return this.rolesMask;
	}

	public void setRolesMask(Long rolesMask) {
		if (rolesMask == null) {
			throw new IllegalArgumentException("rolesMask cannot be null.");
		}

		this.rolesMask = rolesMask;
	}

	public LoginTokenEncoder<LoginToken> getEncoder() {
		return this.encoder;
	}

	public void setEncoder(LoginTokenEncoder<LoginToken> encoder) {
		if (encoder == null) {
			throw new IllegalArgumentException("encoder cannot be null.");
		}

		this.encoder = encoder;
	}

	// MARK: SystemLoginTokenService
	@Override
	public SystemLoginTokenResponse makeSystemToken(SystemLoginTokenRequest request) {
		LoginTokenImpl token = new LoginTokenImpl(SYSTEM_POINTER_TYPE);

		String appId = request.getAppId();
		Long roles = this.maskRoles(request.getRoles());
		Long expiresIn = ValueUtility.defaultTo(request.getExpiresIn(), this.expirationTime);

		if (StringUtility.isEmptyString(appId)) {
			throw new NoAppSpecifiedSystemLoginTokenServiceException();
		}

		token.setApp(appId);
		token.setRoles(roles);
		token.setExpiration(expiresIn);
		token.setRefreshAllowed(false);

		return new SystemTokenResponseImpl(token);
	}

	public Long maskRoles(Long roles) {
		if (roles == null) {
			return this.rolesMask;
		} else {
			LongBitContainer mask = new LongBitContainer(this.getRolesMask());
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
				this.encodedLoginToken = SystemLoginTokenServiceImpl.this.encoder.encodeLoginToken(this.loginToken);
			}

			return this.encodedLoginToken;
		}

		@Override
		public String toString() {
			return "SystemTokenResponseImpl [loginToken=" + this.loginToken + "]";
		}

	}

}
