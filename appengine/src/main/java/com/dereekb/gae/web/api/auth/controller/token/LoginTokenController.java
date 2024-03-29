package com.dereekb.gae.web.api.auth.controller.token;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.EncodedLoginTokenImpl;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.auth.controller.token.impl.TokenValidationRequestImpl;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

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

	// MARK: Authentication
	/**
	 * Creates a refresh token using the current authentication.
	 */
	@ResponseBody
	@RequestMapping(value = "/refresh", method = RequestMethod.GET, produces = "application/json")
	public final LoginTokenPair makeRefreshToken() throws NoSecurityContextException {
		LoginTokenAuthentication<LoginToken> authentication = LoginSecurityContext.getAuthentication();
		String loginToken = authentication.getPrincipal().getDecodedLoginToken().getEncodedLoginToken();
		return this.makeRefreshToken(loginToken);
	}

	/**
	 * Resets all authentication.
	 *
	 * @param loginKey
	 *            Optional key corresponding to a {@link Login}.
	 * @return {@link ApiResponse}. Never {@code null}.
	 * @throws NoSecurityContextException
	 *             thrown if no security is available.
	 */
	@ResponseBody
	@RequestMapping(value = { "/reset", "/reset/{login}" }, method = RequestMethod.POST, produces = "application/json")
	public final ApiResponse resetAuthentication(@PathVariable(value = "login", required = false) String loginKey)
	        throws NoSecurityContextException {
		ApiResponseImpl response = null;

		try {
			ModelKey modified = this.delegate.resetAuthentication(loginKey);
			response = new ApiResponseImpl(true);
			response.setData(new ApiResponseDataImpl("Modified", modified.toString()));
		} catch (NoSecurityContextException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	// MARK: No Authentication
	/**
	 * Validates the input token.
	 */
	@ResponseBody
	@RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded", produces = "application/json")
	public final ApiResponse validateToken(@RequestParam(value = "token", required = true) @NotNull String token,
	                                       @RequestParam(value = "content", required = false) String content,
	                                       @RequestParam(value = "signature", required = false) String signature,
	                                       @RequestParam(value = "quick", required = false) Boolean quick) {
		ApiResponseImpl response = null;

		try {
			TokenValidationRequestImpl request = new TokenValidationRequestImpl(token, content, signature, quick);
			response = this.delegate.validateToken(request);
		} catch (TokenException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	/**
	 * Creates a refresh token using the input authentication token.
	 */
	@ResponseBody
	@RequestMapping(value = "/refresh", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded", produces = "application/json")
	public final LoginTokenPair makeRefreshToken(@RequestParam("accessToken") @NotNull String accessToken) {
		LoginTokenPair response = null;

		try {
			EncodedLoginToken encodedToken = new EncodedLoginTokenImpl(accessToken);
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
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded", produces = "application/json")
	public final LoginTokenPair login(@RequestParam("refreshToken") @NotNull String refreshToken) {
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

}
