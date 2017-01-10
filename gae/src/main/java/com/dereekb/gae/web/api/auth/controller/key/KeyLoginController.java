package com.dereekb.gae.web.api.auth.controller.key;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginRejectedException;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginUnavailableException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * Controller for using a {@link LoginKey} to authenticate.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/login/key")
public class KeyLoginController {

	private KeyLoginControllerDelegate delegate;

	public KeyLoginController(KeyLoginControllerDelegate delegate) throws IllegalArgumentException {
		this.setDelegate(delegate);
	}

	public KeyLoginControllerDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(KeyLoginControllerDelegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("Delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: Enable/Disable
	/**
	 * Enables the login for the current user.
	 * 
	 * @return {@link ApiResponse}. Never {@code null}.
	 * @throws ApiLoginException
	 * @throws ApiRuntimeException
	 */
	@ResponseBody
	@RequestMapping(path = "/enable", method = RequestMethod.PUT, produces = "application/json")
	public final ApiResponse enable() throws ApiRuntimeException {
		ApiResponse response = null;

		try {
			response = this.delegate.enableKeyLogin();
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	/**
	 * Returns the API status for the current user of whether API keys are
	 * enabled or not.
	 * 
	 * @return {@link ApiResponse}. Never {@code null}.
	 * @throws ApiLoginException
	 * @throws ApiRuntimeException
	 */
	@ResponseBody
	@RequestMapping(path = "/status", method = RequestMethod.GET, produces = "application/json")
	public final ApiResponse getStatus() throws ApiRuntimeException {
		ApiResponse response = null;

		try {
			response = this.delegate.getKeyLoginStatus();
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	// MARK: Authenticate
	/**
	 * Attempts to login using an API key/verification KEY pair.
	 *
	 * @param key
	 *            API Key
	 * @param verification
	 *            Verification Key
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public final LoginTokenPair login(@RequestParam @NotEmpty String key,
	                                  @RequestParam @NotEmpty String verification)
	        throws ApiLoginException,
	            ApiRuntimeException {

		LoginTokenPair response = null;

		try {
			response = this.delegate.login(key, verification);
		} catch (KeyLoginRejectedException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.REJECTED, e);
		} catch (KeyLoginUnavailableException e) {
			//Also return rejected to prevent brute-force checking for keys.
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.REJECTED, e);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

}
