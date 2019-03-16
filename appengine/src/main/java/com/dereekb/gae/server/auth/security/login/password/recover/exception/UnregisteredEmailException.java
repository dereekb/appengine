package com.dereekb.gae.server.auth.security.login.password.recover.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when an input email is not registered in the system.
 *
 * @author dereekb
 *
 */
public class UnregisteredEmailException extends PasswordRecoveryException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "UNREGISTERED_EMAIL";
	public static final String ERROR_TITLE = "Unregistered Email";

	private String email;

	public UnregisteredEmailException(String email) {
		this.setReason(email);
	}

	public String getReason() {
		return this.email;
	}

	public void setReason(String email) {
		this.email = email;
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, "The email is not registered.", this.email);
	}

	@Override
	public String toString() {
		return "UnregisteredEmailException [email=" + this.email + "]";
	}

}
