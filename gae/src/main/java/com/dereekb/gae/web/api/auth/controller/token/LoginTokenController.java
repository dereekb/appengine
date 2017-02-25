package com.dereekb.gae.web.api.auth.controller.token;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.EncodedLoginTokenImpl;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;

/**
 * Controller for refresh tokens.
 * 
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/login/auth/token")
public class LoginTokenController {

	private LoginTokenControllerDelegate delegate;

	public LoginTokenController(LoginTokenControllerDelegate delegate) {
		this.setDelegate(delegate);
	}

	public LoginTokenControllerDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(LoginTokenControllerDelegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("Delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: Controller
	/**
	 * Creates a refresh token using the input authentication token.
	 */
	@ResponseBody
	@RequestMapping(value = "/refresh", method = RequestMethod.POST, produces = "application/json")
	public final LoginTokenPair makeRefreshToken(@RequestBody @NotNull String token) {
		LoginTokenPair response = null;

		try {
			EncodedLoginToken encodedToken = new EncodedLoginTokenImpl(token);
			response = this.delegate.makeRefreshToken(encodedToken);
		} catch (TokenException e) {
			throw e;
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	/**
	 * Authenticate using a refresh token.
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public final LoginTokenPair login(@RequestBody @NotNull String refreshToken) {
		LoginTokenPair response = null;

		try {
			EncodedLoginToken encodedToken = new EncodedLoginTokenImpl(refreshToken);
			response = this.delegate.loginWithRefreshToken(encodedToken);
		} catch (TokenException e) {
			throw e;
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	// TODO: Add function that provides information about the current token.

}
