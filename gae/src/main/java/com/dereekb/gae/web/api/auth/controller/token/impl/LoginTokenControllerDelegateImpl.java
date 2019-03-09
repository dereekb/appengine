package com.dereekb.gae.web.api.auth.controller.token.impl;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.refresh.RefreshTokenService;
import com.dereekb.gae.server.auth.security.token.refresh.exception.RefreshTokenExpiredException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.time.exception.RateLimitException;
import com.dereekb.gae.web.api.auth.controller.token.LoginTokenControllerDelegate;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link LoginTokenControllerDelegate} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenControllerDelegateImpl
        implements LoginTokenControllerDelegate {

	private static final boolean ALLOW_REFRESH = false;

	private LoginTokenEncoderDecoder refreshTokenEncoderDecoder;
	private LoginTokenService loginTokenService;
	private RefreshTokenService refreshTokenService;

	public LoginTokenControllerDelegateImpl(LoginTokenService loginTokenService,
	        RefreshTokenService refreshTokenService) {
		this(loginTokenService, loginTokenService, refreshTokenService);
	}

	public LoginTokenControllerDelegateImpl(LoginTokenEncoderDecoder refreshTokenEncoderDecoder,
	        LoginTokenService loginTokenService,
	        RefreshTokenService refreshTokenService) {
		this.setRefreshTokenEncoderDecoder(refreshTokenEncoderDecoder);
		this.setLoginTokenService(loginTokenService);
		this.setRefreshTokenService(refreshTokenService);
	}

	public LoginTokenEncoderDecoder getRefreshTokenEncoderDecoder() {
		return this.refreshTokenEncoderDecoder;
	}

	public void setRefreshTokenEncoderDecoder(LoginTokenEncoderDecoder refreshTokenEncoderDecoder) {
		if (refreshTokenEncoderDecoder == null) {
			throw new IllegalArgumentException("RefreshTokenEncoderDecoder cannot be null.");
		}

		this.refreshTokenEncoderDecoder = refreshTokenEncoderDecoder;
	}

	public LoginTokenService getLoginTokenService() {
		return this.loginTokenService;
	}

	public void setLoginTokenService(LoginTokenService loginTokenService) {
		if (loginTokenService == null) {
			throw new IllegalArgumentException("LoginTokenService cannot be null.");
		}

		this.loginTokenService = loginTokenService;
	}

	public RefreshTokenService getRefreshTokenService() {
		return this.refreshTokenService;
	}

	public void setRefreshTokenService(RefreshTokenService refreshTokenService) {
		if (refreshTokenService == null) {
			throw new IllegalArgumentException("RefreshTokenService cannot be null.");
		}

		this.refreshTokenService = refreshTokenService;
	}

	// MARK: TokenControllerDelegate
	@Override
	public LoginTokenPair makeRefreshToken(EncodedLoginToken token) throws TokenUnauthorizedException {
		DecodedLoginToken loginToken = this.refreshTokenEncoderDecoder.decodeLoginToken(token.getEncodedLoginToken());
		LoginToken refreshToken = this.refreshTokenService.makeRefreshToken(loginToken);
		String encodedToken = this.refreshTokenEncoderDecoder.encodeLoginToken(refreshToken);
		return new LoginTokenPair(encodedToken);
	}

	@Override
	public LoginTokenPair loginWithRefreshToken(EncodedLoginToken refreshToken) throws RefreshTokenExpiredException {
		DecodedLoginToken loginToken = this.refreshTokenEncoderDecoder
		        .decodeLoginToken(refreshToken.getEncodedLoginToken());
		LoginPointer pointer = this.refreshTokenService.loadRefreshTokenPointer(loginToken);
		String encodedToken = this.loginTokenService.encodeLoginToken(pointer, ALLOW_REFRESH);
		return new LoginTokenPair(encodedToken);
	}

	@Override
	public ModelKey resetAuthentication(String loginKeyString)
	        throws NoSecurityContextException,
	            TokenUnauthorizedException,
	            UnavailableModelException,
	            RateLimitException {

		LoginTokenAuthentication authentication = LoginSecurityContext.getAuthentication();
		LoginTokenUserDetails details = authentication.getPrincipal();

		ModelKey key = null;

		switch (details.getUserType()) {
			case ADMINISTRATOR:
				if (loginKeyString != null) {
					key = ModelKey.convertNumberString(loginKeyString);
				} else {
					key = details.getLoginKey();
				}
				break;
			case USER:
				key = details.getLoginKey();
				break;
			default:
				break;
		}

		if (key == null) {
			throw new TokenUnauthorizedException();
		}

		this.refreshTokenService.resetAuthentication(key);

		return key;
	}

}
