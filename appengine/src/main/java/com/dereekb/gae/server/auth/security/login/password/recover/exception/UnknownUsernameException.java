package com.dereekb.gae.server.auth.security.login.password.recover.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when an input username is not in the system.
 *
 * @author dereekb
 *
 */
public class UnknownUsernameException extends PasswordRecoveryException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "UNKNOWN_USERNAME";
	public static final String ERROR_TITLE = "Unknown Username";

	private String username;

	public UnknownUsernameException(String username) {
		this.setReason(username);
	}

	public String getReason() {
		return this.username;
	}

	public void setReason(String username) {
		this.username = username;
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, "The username is unknown.", this.username);
	}

	@Override
	public String toString() {
		return "UnknownUsernameException [username=" + this.username + "]";
	}

}
