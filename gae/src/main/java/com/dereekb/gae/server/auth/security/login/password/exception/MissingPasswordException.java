package com.dereekb.gae.server.auth.security.login.password.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * {@link PasswordRecoveryException} that is related to {@link MissingPasswordException].
 *
 * @author dereekb
 *
 */
public class MissingPasswordException extends PasswordException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "PASSWORD_MISSING";
	public static final String ERROR_TITLE = "Password Missing";

	public MissingPasswordException() {}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);
	}

	@Override
	public String toString() {
		return "MissingPasswordException []";
	}

}
