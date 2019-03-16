package com.dereekb.gae.server.auth.security.login.password.recover.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when an error occurs while trying to verify a user's email.
 *
 * @author dereekb
 *
 */
public class PasswordRecoveryVerificationException extends PasswordRecoveryException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "RECOVERY_TOKEN_ERROR";
	public static final String ERROR_TITLE = "Recovery Token Error";

	public PasswordRecoveryVerificationException() {
		super();
	}

	public PasswordRecoveryVerificationException(Throwable cause) {
		super(cause);
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);
	}

	@Override
	public String toString() {
		return "PasswordRecoveryVerificationException []";
	}

}
