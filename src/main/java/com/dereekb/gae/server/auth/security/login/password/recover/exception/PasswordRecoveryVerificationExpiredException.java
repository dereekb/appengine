package com.dereekb.gae.server.auth.security.login.password.recover.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when the verification token has expired.
 *
 * @author dereekb
 *
 */
public class PasswordRecoveryVerificationExpiredException extends PasswordRecoveryVerificationException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "TOKEN_EXPIRED_ERROR";
	public static final String ERROR_TITLE = "Token Expired Error";

	public PasswordRecoveryVerificationExpiredException() {
		super();
	}

	public PasswordRecoveryVerificationExpiredException(Throwable cause) {
		super(cause);
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);
	}

	@Override
	public String toString() {
		return "PasswordRecoveryVerificationExpiredException []";
	}

}
