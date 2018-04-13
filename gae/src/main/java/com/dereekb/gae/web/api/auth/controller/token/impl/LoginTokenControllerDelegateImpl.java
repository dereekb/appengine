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
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
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

	private static final boolean ALLOW_REFRESH = false;

	private LoginTokenEncoderDecoder<LoginToken> refreshTokenEncoderDecoder;
	private LoginTokenService<LoginToken> loginTokenService;
	private RefreshTokenService refreshTokenService;
	private AppLoginSecurityVerifierService verifierService;

	public LoginTokenControllerDelegateImpl(LoginTokenService<LoginToken> loginTokenService,
	        RefreshTokenService refreshTokenService) {
		this(loginTokenService, loginTokenService, refreshTokenService);
	}

	public LoginTokenControllerDelegateImpl(LoginTokenEncoderDecoder<LoginToken> refreshTokenEncoderDecoder,
	        LoginTokenService<LoginToken> loginTokenService,
	        RefreshTokenService refreshTokenService) {
		this.setRefreshTokenEncoderDecoder(refreshTokenEncoderDecoder);
		this.setLoginTokenService(loginTokenService);
		this.setRefreshTokenService(refreshTokenService);
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

		return response;
	}

	@Override
	public LoginTokenPair makeRefreshToken(EncodedLoginToken token) throws TokenUnauthorizedException {
		DecodedLoginToken<LoginToken> decodedLoginToken = this.refreshTokenEncoderDecoder
		        .decodeLoginToken(token.getEncodedLoginToken());
		LoginToken refreshToken = this.refreshTokenService.makeRefreshToken(decodedLoginToken.getLoginToken());
		String encodedToken = this.refreshTokenEncoderDecoder.encodeLoginToken(refreshToken);
		return new LoginTokenPair(encodedToken);
	}

	@Override
	public LoginTokenPair loginWithRefreshToken(EncodedLoginToken refreshToken) throws RefreshTokenExpiredException {
		DecodedLoginToken<LoginToken> decodedLoginToken = this.refreshTokenEncoderDecoder
		        .decodeLoginToken(refreshToken.getEncodedLoginToken());
		LoginPointer pointer = this.refreshTokenService.loadRefreshTokenPointer(decodedLoginToken.getLoginToken());
		String encodedToken = this.loginTokenService.encodeLoginToken(pointer, ALLOW_REFRESH);
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
