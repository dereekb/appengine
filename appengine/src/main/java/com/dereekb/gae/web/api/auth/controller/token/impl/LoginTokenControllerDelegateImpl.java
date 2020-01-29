package com.dereekb.gae.web.api.auth.controller.token.impl;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityVerifierService;
import com.dereekb.gae.server.auth.security.app.service.LoginTokenVerifierRequest;
import com.dereekb.gae.server.auth.security.app.service.impl.LoginTokenVerifierRequestImpl;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenBuilderOptions;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenBuilderOptionsImpl;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.refresh.RefreshTokenService;
import com.dereekb.gae.server.auth.security.token.refresh.exception.RefreshTokenExpiredException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.utilities.time.exception.RateLimitException;
import com.dereekb.gae.web.api.auth.controller.token.LoginTokenControllerDelegate;
import com.dereekb.gae.web.api.auth.controller.token.TokenValidationRequest;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

import io.jsonwebtoken.Claims;

/**
 * {@link LoginTokenControllerDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenControllerDelegateImpl
        implements LoginTokenControllerDelegate {

	private static final boolean DISALLOW_REFRESH = false;

	private LoginTokenEncoderDecoder<LoginToken> refreshTokenEncoderDecoder;
	private LoginTokenService<LoginToken> loginTokenService;
	private RefreshTokenService refreshTokenService;
	private AppLoginSecurityVerifierService verifierService;

	public LoginTokenControllerDelegateImpl(LoginTokenService<LoginToken> loginTokenService,
	        RefreshTokenService refreshTokenService,
	        AppLoginSecurityVerifierService verifierService) {
		this(loginTokenService, loginTokenService, refreshTokenService, verifierService);
	}

	public LoginTokenControllerDelegateImpl(LoginTokenEncoderDecoder<LoginToken> refreshTokenEncoderDecoder,
	        LoginTokenService<LoginToken> loginTokenService,
	        RefreshTokenService refreshTokenService,
	        AppLoginSecurityVerifierService verifierService) {
		this.setRefreshTokenEncoderDecoder(refreshTokenEncoderDecoder);
		this.setLoginTokenService(loginTokenService);
		this.setRefreshTokenService(refreshTokenService);
		this.setVerifierService(verifierService);
	}

	public LoginTokenEncoderDecoder<LoginToken> getRefreshTokenEncoderDecoder() {
		return this.refreshTokenEncoderDecoder;
	}

	public void setRefreshTokenEncoderDecoder(LoginTokenEncoderDecoder<LoginToken> refreshTokenEncoderDecoder) {
		if (refreshTokenEncoderDecoder == null) {
			throw new IllegalArgumentException("RefreshTokenEncoderDecoder cannot be null.");
		}

		this.refreshTokenEncoderDecoder = refreshTokenEncoderDecoder;
	}

	public LoginTokenService<LoginToken> getLoginTokenService() {
		return this.loginTokenService;
	}

	public void setLoginTokenService(LoginTokenService<LoginToken> loginTokenService) {
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

	public AppLoginSecurityVerifierService getVerifierService() {
		return this.verifierService;
	}

	public void setVerifierService(AppLoginSecurityVerifierService verifierService) {
		if (verifierService == null) {
			throw new IllegalArgumentException("verifierService cannot be null.");
		}

		this.verifierService = verifierService;
	}

	// MARK: TokenControllerDelegate
	@Override
	public ApiResponseImpl validateToken(TokenValidationRequest request) throws TokenException {
		String token = request.getToken();
		DecodedLoginToken<LoginToken> loginToken = this.loginTokenService.decodeLoginToken(token);

		String content = request.getContent();
		String signature = request.getSignature();

		LoginTokenVerifierRequest verifierRequest = new LoginTokenVerifierRequestImpl(loginToken, content, signature);
		this.verifierService.assertValidTokenSignature(verifierRequest);

		ApiResponseImpl response = new ApiResponseImpl();

		boolean quick = ValueUtility.valueOf(request.getQuick());
		if (!quick) {
			Claims claims = loginToken.getClaims();
			Map<String, Object> claimsMap = new HashMap<String, Object>(claims);
			ApiResponseData data = new ApiResponseDataImpl("TOKEN_CLAIMS", claimsMap);

			// TODO: Set API safe login token data instead/too?

			response.setData(data);
		}

		// Add error notice
		if (request.getSignature() != null && loginToken.getLoginToken().getApp() == null) {
			response.addError(new ApiResponseErrorImpl("UNUSED_SIGNATURE", "Unused Signature",
			        "Token has no app association. Signature unnecessary."));
		}

		return response;
	}

	@Override
	public LoginTokenPair makeRefreshToken(EncodedLoginToken token) throws TokenUnauthorizedException {
		/*
		 * We decode a token using the LoginTokenService since we don't want
		 * refresh tokens to be able to refresh themselves.
		 *
		 * Refresh token signing has their own secret/signature to prevent them
		 * from being decoded properly here, but also the token service rejects
		 * tokens with a refresh token type.
		 */
		DecodedLoginToken<LoginToken> decodedLoginToken = this.loginTokenService
		        .decodeLoginToken(token.getEncodedLoginToken());
		LoginToken refreshToken = this.refreshTokenService.makeRefreshToken(decodedLoginToken.getLoginToken());
		String encodedToken = this.refreshTokenEncoderDecoder.encodeLoginToken(refreshToken);
		return new LoginTokenPair(encodedToken);
	}

	@Override
	public LoginTokenPair loginWithRefreshToken(EncodedLoginToken refreshToken,
	                                            Long rolesMask)
	        throws RefreshTokenExpiredException {
		DecodedLoginToken<LoginToken> decodedLoginToken = this.refreshTokenEncoderDecoder
		        .decodeLoginToken(refreshToken.getEncodedLoginToken());
		LoginPointer pointer = this.refreshTokenService.loadRefreshTokenPointer(decodedLoginToken.getLoginToken());

		LoginTokenBuilderOptions options = new LoginTokenBuilderOptionsImpl(DISALLOW_REFRESH, rolesMask);

		String encodedToken = this.loginTokenService.encodeLoginToken(pointer, options);
		return new LoginTokenPair(encodedToken);
	}

	@Override
	public ModelKey resetAuthentication(String loginKeyString)
	        throws NoSecurityContextException,
	            TokenUnauthorizedException,
	            UnavailableModelException,
	            RateLimitException {

		LoginTokenAuthentication<LoginToken> authentication = LoginSecurityContext.getAuthentication();
		LoginTokenUserDetails<LoginToken> details = authentication.getPrincipal();

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
