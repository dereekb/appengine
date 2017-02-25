package com.dereekb.gae.server.auth.security.token.refresh.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.server.auth.security.token.refresh.RefreshTokenService;
import com.dereekb.gae.server.auth.security.token.refresh.exception.AuthenticationPurgeException;
import com.dereekb.gae.server.auth.security.token.refresh.exception.RefreshTokenExpiredException;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.time.DateUtility;

/**
 * {@link RefreshTokenService} implementation.
 * 
 * @author dereekb
 *
 */
public class RefreshTokenServiceImpl
        implements RefreshTokenService {

	// 60 Days Default
	private static final Long DEFAULT_EXPIRATION_TIME = 60 * 24 * 60 * 60 * 1000L;

	private Getter<Login> loginGetter;
	private Getter<LoginPointer> loginPointerGetter;

	private Long expirationTime = DEFAULT_EXPIRATION_TIME;

	public RefreshTokenServiceImpl(Getter<Login> loginGetter, Getter<LoginPointer> loginPointerGetter) {
		this.setLoginGetter(loginGetter);
		this.setLoginPointerGetter(loginPointerGetter);
	}

	public Getter<Login> getLoginGetter() {
		return this.loginGetter;
	}

	public void setLoginGetter(Getter<Login> loginGetter) {
		if (loginGetter == null) {
			throw new IllegalArgumentException("LoginGetter cannot be null.");
		}

		this.loginGetter = loginGetter;
	}

	public Getter<LoginPointer> getLoginPointerGetter() {
		return this.loginPointerGetter;
	}

	public void setLoginPointerGetter(Getter<LoginPointer> loginPointerGetter) {
		if (loginPointerGetter == null) {
			throw new IllegalArgumentException("LoginPointerGetter cannot be null.");
		}

		this.loginPointerGetter = loginPointerGetter;
	}

	public Long getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Long expirationTime) {
		if (expirationTime == null) {
			throw new IllegalArgumentException("ExpirationTime cannot be null.");
		}

		this.expirationTime = expirationTime;
	}

	@Override
	public LoginToken makeRefreshToken(LoginToken loginToken)
	        throws AuthenticationPurgeException,
	            TokenUnauthorizedException {

		String loginPointerId = loginToken.getLoginPointerId();
		LoginPointer loginPointer = this.loadLoginPointer(loginPointerId);
		Login login = this.loadLogin(loginPointer);

		this.assertAuthenticationValid(login, loginToken);

		LoginTokenImpl refreshToken = new LoginTokenImpl();

		refreshToken.setSubject(loginToken.getSubject());
		refreshToken.setPointerType(LoginPointerType.REFRESH_TOKEN);
		refreshToken.setLoginPointer(loginPointerId);
		refreshToken.setToExpireIn(DEFAULT_EXPIRATION_TIME);

		return refreshToken;
	}

	@Override
	public LoginPointer loadRefreshTokenPointer(LoginToken refreshToken) throws RefreshTokenExpiredException {
		LoginPointer loginPointer = null;

		try {
			String loginPointerId = refreshToken.getLoginPointerId();
			loginPointer = this.loadLoginPointer(loginPointerId);
			Login login = this.loadLogin(loginPointer);
			this.assertAuthenticationValid(login, refreshToken);
		} catch (AuthenticationPurgeException e) {
			throw new RefreshTokenExpiredException(e);
		}

		return loginPointer;
	}

	// MARK: Internal
	private void assertAuthenticationValid(Login login,
	                                       LoginToken loginToken) {
		Date authReset = login.getAuthReset();
		Date authIssued = loginToken.getIssued();

		if (DateUtility.dateIsAfterDate(authReset, authIssued)) {
			throw new AuthenticationPurgeException("Purged due to authentication reset.");
		}
	}

	private Login loadLogin(LoginPointer loginPointer) throws AuthenticationPurgeException {
		ModelKey loginKey = loginPointer.getLoginModelKey();
		Login login = this.loginGetter.get(loginKey);

		if (login == null) {
			throw new AuthenticationPurgeException("Login was purged.");
		}

		return login;
	}

	private LoginPointer loadLoginPointer(String loginPointerId) throws AuthenticationPurgeException {
		ModelKey loginPointerKey = ModelKey.safe(loginPointerId);
		if (loginPointerKey == null) {
			throw new TokenUnauthorizedException("Token disallowed for refresh token.");
		}

		LoginPointer loginPointer = this.loginPointerGetter.get(loginPointerKey);
		if (loginPointer == null) {
			throw new AuthenticationPurgeException("Original authentication was purged.");
		}

		return loginPointer;
	}

}
