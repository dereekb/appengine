package com.dereekb.gae.web.api.auth.controller.register;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginRegistrationRejectedException;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;

/**
 * API for registering a new login, or linking a login to another login.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/login/auth/register")
public class LoginRegisterController {

	private LoginRegisterControllerDelegate delegate;

	public LoginRegisterController(LoginRegisterControllerDelegate delegate) {
		this.setDelegate(delegate);
	}

	public LoginRegisterControllerDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(LoginRegisterControllerDelegate delegate) {
		this.delegate = delegate;
	}

	// MARK: Controller
	/**
	 * Creates a new login using the current user and token in the context, and returns the updated
	 * {@link LoginTokenPair}.
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public final LoginTokenPair register() {
		LoginTokenPair response = null;

		try {
			response = this.delegate.register();
		} catch (LoginExistsException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.EXISTS, e);
		} catch (LoginRegistrationRejectedException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.REJECTED, e);
		} catch (TokenException e) {
			throw e;
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	/**
	 * Registers one or more {@link LoginPointer} values with a single
	 * {@link Login}.
	 * <p>
	 * Returns a new {@link LoginTokenPair}.
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(path = "/tokens", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public final void registerTokensWithLogin(@RequestBody @NotEmpty @Min(2) List<String> tokens) {

		// TODO: Need to update this to use the current security.

		try {
			this.delegate.registerLogins(tokens);
		} catch (TokenException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}
	}

}
