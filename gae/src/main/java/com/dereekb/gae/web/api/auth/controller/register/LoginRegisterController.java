package com.dereekb.gae.web.api.auth.controller.register;

import java.util.List;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.model.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;

/**
 * API for registering a new login, or linking a login to another login.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/login/register")
public class LoginRegisterController {

	private LoginRegisterControllerDelegate delegate;

    public LoginRegisterControllerDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(LoginRegisterControllerDelegate delegate) {
		this.delegate = delegate;
	}

	// MARK: Controller
	/**
	 * Creates a new login using the current user, and returns the updated
	 * {@link LoginTokenPair}.
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public final LoginTokenPair register() {
		LoginTokenPair response = null;

		try {
			response = this.delegate.register();
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		} catch (LoginExistsException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.EXISTS, e);
		}

		return response;
	}

	/**
	 * Registers one or more {@link LoginPointer} values with a single
	 * {@link Login}.
	 * <p>
	 * Returns a new {@link LoginTokenPair}.
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public final void registerLogins(@RequestParam @NotEmpty @Min(2) List<String> tokens) {
		try {
			this.delegate.registerLogins(tokens);
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}
	}

	// TODO: Multi-login: Create function for creating/deleting a child login.

}
