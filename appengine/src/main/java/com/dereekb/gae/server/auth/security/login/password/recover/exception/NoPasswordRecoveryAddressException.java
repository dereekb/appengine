package com.dereekb.gae.server.auth.security.login.password.recover.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when an error occurs while trying to send an email.
 *
 * @author dereekb
 *
 */
public class NoPasswordRecoveryAddressException extends PasswordRecoveryMailException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "NO_RECOVERY_ADDRESS_ERROR";
	public static final String ERROR_TITLE = "No Recovery Address Error";

	public NoPasswordRecoveryAddressException() {
		super();
	}

	public NoPasswordRecoveryAddressException(Throwable cause) {
		super(cause);
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);
	}

	@Override
	public String toString() {
		return "NoPasswordRecoveryAddressException []";
	}

}
