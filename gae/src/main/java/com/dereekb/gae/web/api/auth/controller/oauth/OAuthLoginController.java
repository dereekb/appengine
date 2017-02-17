package com.dereekb.gae.web.api.auth.controller.oauth;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthServiceUnavailableException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginErrorException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginInvalidException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.exception.ApiCaughtRuntimeException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;

/**
 * Controller for logging in using oAuth.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/login/auth/oauth")
public final class OAuthLoginController {

	private OAuthLoginControllerDelegate delegate;

	public OAuthLoginController(OAuthLoginControllerDelegate delegate) throws IllegalArgumentException {
		this.setDelegate(delegate);
	}

	public OAuthLoginControllerDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(OAuthLoginControllerDelegate delegate) throws IllegalArgumentException {
		if (delegate == null) {
			throw new IllegalArgumentException("Delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: Controller
	/**
	 * OAuth Login used by applications that have already retrieved an access
	 * token.
	 *
	 * @param type
	 *            OAuth service/type. Example "google".
	 * @param authCode
	 *            OAuth authorization code.
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 */
	@ResponseBody
	@RequestMapping(path = "/{type}/code", method = RequestMethod.POST, produces = "application/json")
	public final LoginTokenPair loginWithAuthCode(@PathVariable("type") String type,
	                                              @RequestParam("code") @NotEmpty String authCode,
	                                              @RequestParam("type") String codeType)
	        throws ApiLoginException,
	            ApiCaughtRuntimeException {
		LoginTokenPair response = null;

		try {
			response = this.delegate.loginWithAuthCode(type, authCode, codeType);
		} catch (OAuthAuthorizationTokenRequestException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.INVALID_CREDENTIALS, e);
		} catch (OAuthServiceUnavailableException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.UNSUPPORTED, e);
		} catch (OAuthInsufficientException e) {
			throw new ApiLoginInvalidException(e);
		} catch (OAuthConnectionException e) {
			throw new ApiLoginErrorException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	/**
	 * OAuth Login used by applications that have already retrieved an access
	 * token.
	 *
	 * @param type
	 *            OAuth service/type. Example "google".
	 * @param accessToken
	 *            OAuth access token retrieved by the client before connecting.
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 */
	@ResponseBody
	@RequestMapping(path = "/{type}/token", method = RequestMethod.POST, produces = "application/json")
	public final LoginTokenPair loginWithAccessToken(@PathVariable("type") String type,
	                                                 @RequestParam("token") @NotEmpty String accessToken)
	        throws ApiLoginException,
	            ApiCaughtRuntimeException {
		LoginTokenPair response = null;

		try {
			response = this.delegate.loginWithAccessToken(type, accessToken);
		} catch (OAuthAuthorizationTokenRequestException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.INVALID_CREDENTIALS, e);
		} catch (OAuthServiceUnavailableException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.UNSUPPORTED, e);
		} catch (OAuthInsufficientException e) {
			throw new ApiLoginInvalidException(e);
		} catch (OAuthConnectionException e) {
			throw new ApiLoginErrorException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	@Override
	public String toString() {
		return "OAuthLoginController [delegate=" + this.delegate + "]";
	}

}
