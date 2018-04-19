package com.dereekb.gae.server.auth.security.login.password.recover.exception;

import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Exception thrown by the password recovery service.
 *
 * @author dereekb
 *
 */
public class PasswordRecoveryException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "PASSWORD_RECOVERY_ERROR";
	public static final String ERROR_TITLE = "Password Recovery Error";

	public PasswordRecoveryException() {
		super();
	}

	public PasswordRecoveryException(String message) {
		super(message);
	}

	public PasswordRecoveryException(Throwable cause) {
		super(cause);
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, this.getMessage());
	}

}
