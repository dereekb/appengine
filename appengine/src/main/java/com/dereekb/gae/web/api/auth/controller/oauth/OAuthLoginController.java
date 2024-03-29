package com.dereekb.gae.web.api.auth.controller.oauth;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthenticationException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthServiceUnavailableException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.handler.OAuthFailureResolver;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
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

	// MARK: OAuth Resource / Authorization Server

	// MARK: Login With OAuth Providers
	/**
	 * OAuth Login used by applications that have only received an
	 * authorization code.
	 * <p>
	 * This is typically used in cases where the SDK's provided by the oauth
	 * provider are not being used. In most cases you'll use
	 * {@link #loginWithAccessToken(String, String)}.
	 *
	 * @param type
	 *            OAuth service/type. Example "google".
	 * @param authCode
	 *            OAuth authorization code.
	 * @param codeType
	 *            Optional code type. May be {@code null}.
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 */
	@ResponseBody
	@RequestMapping(path = "/{type}/code", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded", produces = "application/json")
	public final LoginTokenPair loginWithAuthCode(@PathVariable("type") String type,
	                                              @RequestParam("code") @NotEmpty String authCode,
	                                              @RequestParam(name = "type", required = false) String codeType)
	        throws ApiLoginException,
	            ApiCaughtRuntimeException {
		LoginTokenPair response = null;

		try {
			response = this.delegate.loginWithAuthCode(type, authCode, codeType);
		} catch (OAuthAuthenticationException e) {
			OAuthFailureResolver.resolve(e);
		} catch (OAuthServiceUnavailableException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.UNSUPPORTED, e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	/**
	 * OAuth Login used by applications that have already retrieved a short-term
	 * web access token that can only be used by our server.
	 * <p>
	 * The server will use this short-term token to get the data it needs to
	 * complete logging into our site.
	 *
	 * @param type
	 *            OAuth service/type. Example "google".
	 * @param accessToken
	 *            OAuth access token retrieved by the client before connecting.
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 */
	@ResponseBody
	@RequestMapping(path = "/{type}/token", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded", produces = "application/json")
	public final LoginTokenPair loginWithAccessToken(@PathVariable("type") String type,
	                                                 @RequestParam("token") @NotEmpty String accessToken)
	        throws ApiLoginException,
	            ApiCaughtRuntimeException {
		LoginTokenPair response = null;

		try {
			response = this.delegate.loginWithAccessToken(type, accessToken);
		} catch (OAuthAuthenticationException e) {
			OAuthFailureResolver.resolve(e);
		} catch (OAuthServiceUnavailableException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.UNSUPPORTED, e);
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
