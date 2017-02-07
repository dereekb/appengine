package com.dereekb.gae.web.api.auth.controller.password;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.security.login.exception.InvalidLoginCredentialsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginPair;
import com.dereekb.gae.web.api.auth.controller.password.impl.PasswordLoginPairImpl;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginExistsException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginInvalidException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;

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

	// MARK: Controller
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
			throw new ApiRuntimeException(e);
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
		} catch (LoginExistsException e) {
			throw new ApiLoginExistsException(e);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	@Override
	public String toString() {
		return "PasswordLoginController [delegate=" + this.delegate + "]";
	}

}
