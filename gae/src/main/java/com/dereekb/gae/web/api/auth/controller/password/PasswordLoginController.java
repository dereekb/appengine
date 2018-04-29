package com.dereekb.gae.web.api.auth.controller.password;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.security.login.exception.InvalidLoginCredentialsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginPair;
import com.dereekb.gae.server.auth.security.login.password.exception.PasswordRestrictionException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerifiedException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnknownUsernameException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnregisteredEmailException;
import com.dereekb.gae.web.api.auth.controller.password.impl.PasswordLoginPairImpl;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginExistsException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginInvalidException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.exception.WrappedApiBadRequestException;
import com.dereekb.gae.web.api.exception.WrappedApiNotFoundException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * API used for simple username/password logins.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/login/auth/pass")
public final class PasswordLoginController {

	private PasswordLoginControllerDelegate delegate;

	public PasswordLoginController(PasswordLoginControllerDelegate delegate) throws IllegalArgumentException {
		this.setDelegate(delegate);
	}

	public PasswordLoginControllerDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(PasswordLoginControllerDelegate delegate) throws IllegalArgumentException {
		if (delegate == null) {
			throw new IllegalArgumentException("Delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: Login
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public final LoginTokenPair login(@RequestParam @NotEmpty String username,
	                                  @RequestParam @NotEmpty String password) {
		LoginTokenPair response = null;

		try {
			PasswordLoginPair loginPair = new PasswordLoginPairImpl(username, password);
			response = this.delegate.login(loginPair);
		} catch (LoginUnavailableException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.UNAVAILABLE, e);
		} catch (InvalidLoginCredentialsException e) {
			throw new ApiLoginInvalidException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	public final LoginTokenPair create(@RequestParam @NotEmpty String username,
	                                   @RequestParam @NotEmpty String password) {
		LoginTokenPair response = null;

		try {
			PasswordLoginPair loginPair = new PasswordLoginPairImpl(username, password);
			response = this.delegate.createLogin(loginPair);
		} catch (PasswordRestrictionException e) {
			throw new WrappedApiBadRequestException(e);
		} catch (LoginExistsException e) {
			throw new ApiLoginExistsException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	// MARK: Email

	// TODO: Change email. Requires the user to be logged in.

	// MARK: Recovery
	/**
	 * Send or resends a verification email to the requested email.
	 *
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/verify/send", method = RequestMethod.POST, produces = "application/json")
	public final ApiResponse sendVerificationEmail(@RequestParam String email) {
		ApiResponseImpl response = new ApiResponseImpl();

		try {
			this.delegate.sendVerificationEmail(email);
		} catch (PasswordRecoveryVerifiedException e) {
			response.addError(e.asResponseError());
		} catch (UnregisteredEmailException e) {
			throw new WrappedApiNotFoundException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/verify/token", method = RequestMethod.POST, produces = "application/json")
	public final ApiResponse verifyEmail(@RequestParam String username) {
		ApiResponseImpl response = new ApiResponseImpl();

		try {
			this.delegate.recoverPassword(username);
		} catch (UnknownUsernameException e) {
			throw new WrappedApiNotFoundException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/recover/password", method = RequestMethod.POST, produces = "application/json")
	public final ApiResponse recoverPassword(@RequestParam String username) {
		ApiResponseImpl response = new ApiResponseImpl();

		try {
			this.delegate.recoverPassword(username);
		} catch (UnknownUsernameException e) {
			throw new WrappedApiNotFoundException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/recover/username", method = RequestMethod.POST, produces = "application/json")
	public final ApiResponse recoverUsername(@RequestParam String email) {
		ApiResponseImpl response = new ApiResponseImpl();

		try {
			this.delegate.recoverUsername(email);
		} catch (UnregisteredEmailException e) {
			throw new WrappedApiNotFoundException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	// TODO: Add Password Verification and Recovery Components!

	@Override
	public String toString() {
		return "PasswordLoginController [delegate=" + this.delegate + "]";
	}

}
