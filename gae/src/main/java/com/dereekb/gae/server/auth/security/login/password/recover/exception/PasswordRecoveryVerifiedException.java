package com.dereekb.gae.server.auth.security.login.password.recover.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when the account has already been verified by username.
 *
 * @author dereekb
 *
 */
public class PasswordRecoveryVerifiedException extends PasswordRecoveryException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "EMAIL_VERIFIED";
	public static final String ERROR_TITLE = "Email Verified";

	private String username;

	public PasswordRecoveryVerifiedException(String username) {
		this.setUsername(username);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, "The email for this user is already verified.", this.username);
	}

	@Override
	public String toString() {
		return "PasswordRecoveryVerifiedException [username=" + this.username + "]";
	}

}
