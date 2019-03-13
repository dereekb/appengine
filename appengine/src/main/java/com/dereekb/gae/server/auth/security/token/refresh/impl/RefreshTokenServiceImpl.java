package com.dereekb.gae.server.auth.security.token.refresh.impl;

import java.util.Date;
import java.util.Set;

import com.dereekb.gae.model.exception.UnavailableModelException;
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
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.time.DateUtility;
import com.dereekb.gae.utilities.time.exception.RateLimitException;

/**
 * {@link RefreshTokenService} implementation.
 *
 * @author dereekb
 *
 */
public class RefreshTokenServiceImpl
        implements RefreshTokenService {

	// 5 Seconds of Precision
	public static final Long DEFAULT_PRECISION = 5000L;

	// 2 minute authentication reset cooldown.
	public static final Long DEFAULT_RESET_COOLDOWN = 2 * 60 * 1000L;

	// 90 Days Default
	private static final Long DEFAULT_EXPIRATION_TIME = 90 * 24 * 60 * 60 * 1000L;

	private static Set<LoginPointerType> BLACK_LISTED_TYPES = SetUtility.makeSet(LoginPointerType.ANONYMOUS,
	        LoginPointerType.API_KEY, LoginPointerType.SYSTEM);

	private Long timePrecision = DEFAULT_PRECISION;
	private Long resetCooldown = DEFAULT_RESET_COOLDOWN;

	private GetterSetter<Login> loginGetterSetter;
	private Getter<LoginPointer> loginPointerGetter;
	private Set<LoginPointerType> typeBlackList = BLACK_LISTED_TYPES;

	private Long expirationTime = DEFAULT_EXPIRATION_TIME;

	public RefreshTokenServiceImpl(GetterSetter<Login> loginGetterSetter, Getter<LoginPointer> loginPointerGetter) {
		this.setLoginGetterSetter(loginGetterSetter);
		this.setLoginPointerGetter(loginPointerGetter);
	}

	public GetterSetter<Login> getLoginGetterSetter() {
		return this.loginGetterSetter;
	}

	public void setLoginGetterSetter(GetterSetter<Login> loginGetterSetter) {
		if (loginGetterSetter == null) {
			throw new IllegalArgumentException("LoginGetterSetter cannot be null.");
		}

		this.loginGetterSetter = loginGetterSetter;
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

	public Set<LoginPointerType> getTypeBlackList() {
		return this.typeBlackList;
	}

	public void setTypeBlackList(Set<LoginPointerType> typeBlackList) {
		if (typeBlackList == null) {
			throw new IllegalArgumentException("TypeBlackList cannot be null.");
		}

		this.typeBlackList = typeBlackList;
	}

	public Long getTimePrecision() {
		return this.timePrecision;
	}

	public void setTimePrecision(Long timePrecision) {
		if (timePrecision == null) {
			throw new IllegalArgumentException("TimePrecision cannot be null.");
		}

		this.timePrecision = timePrecision;
	}

	public Long getResetCooldown() {
		return this.resetCooldown;
	}

	public void setResetCooldown(Long resetCooldown) {
		if (resetCooldown == null) {
			throw new IllegalArgumentException("ResetCooldown cannot be null.");
		}

		this.resetCooldown = resetCooldown;
	}

	// MARK: RefreshTokenService
	@Override
	public LoginToken makeRefreshToken(LoginToken loginToken)
	        throws AuthenticationPurgeException,
	            TokenUnauthorizedException {

		// TODO: Add options to specify refresh time limit.

		Long loginId = loginToken.getLoginId();
		String loginPointerId = loginToken.getLoginPointerId();
		LoginPointer loginPointer = this.loadLoginPointer(loginPointerId);

		if (this.typeBlackList.contains(loginPointer.getLoginPointerType())) {
			throw new TokenUnauthorizedException("Cannot create refresh token with this type of token.");
		}

		Login login = this.loadLogin(loginPointer);

		this.assertRefreshIsAllowed(loginToken);
		this.assertAuthenticationValid(login, loginToken);

		LoginTokenImpl refreshToken = new LoginTokenImpl();

		refreshToken.setRefreshAllowed(false);
		refreshToken.setLogin(loginId);
		refreshToken.setSubject(loginToken.getSubject());
		refreshToken.setPointerType(LoginPointerType.REFRESH_TOKEN);
		refreshToken.setLoginPointer(loginPointerId);
		refreshToken.setToExpireIn(DEFAULT_EXPIRATION_TIME);

		return refreshToken;
	}

	@Override
	public LoginPointer loadRefreshTokenPointer(LoginToken refreshToken)
	        throws RefreshTokenExpiredException,
	            TokenUnauthorizedException {
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

	private void assertRefreshIsAllowed(LoginToken refreshToken) throws TokenUnauthorizedException {
		if (refreshToken.isRefreshAllowed() == false) {
			throw new TokenUnauthorizedException("This token is not authorized for refreshing.");
		}
	}

	@Override
	public void resetAuthentication(ModelKey loginKey) throws UnavailableModelException, RateLimitException {
		Login login = this.loginGetterSetter.get(loginKey);

		if (login == null) {
			throw new UnavailableModelException(loginKey);
		}

		Date lastResetDate = login.getAuthReset();

		if (DateUtility.timeHasPassed(lastResetDate, this.resetCooldown) == false) {
			throw new RateLimitException("Too early to reset authentication.");
		}

		this.resetAuthentication(login);
	}

	public void resetAuthentication(Login login) {
		login.setAuthReset(new Date());
		this.loginGetterSetter.update(login);
	}

	// MARK: Internal
	private void assertAuthenticationValid(Login login,
	                                       LoginToken loginToken)
	        throws AuthenticationPurgeException {
		// Round due to JWT issues.
		Date authReset = login.getAuthReset();
		Date authIssued = loginToken.getIssued();

		if (DateUtility.dateIsAfterDate(authReset, authIssued, this.timePrecision)) {
			throw new AuthenticationPurgeException("Purged due to authentication reset.");
		}
	}

	private Login loadLogin(LoginPointer loginPointer) throws AuthenticationPurgeException {
		ModelKey loginKey = loginPointer.getLoginOwnerKey();

		if (loginKey == null) {
			throw new AuthenticationPurgeException("Login was purged or never existed.");
		}

		Login login = this.loginGetterSetter.get(loginKey);

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
