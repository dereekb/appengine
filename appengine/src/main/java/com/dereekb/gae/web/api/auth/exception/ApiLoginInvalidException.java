package com.dereekb.gae.web.api.auth.exception;

import com.dereekb.gae.server.auth.security.login.exception.LoginAuthenticationException;

/**
 * Invalid credentials exception.
 *
 * @author dereekb
 *
 */
public class ApiLoginInvalidException extends ApiLoginException {

	private static final long serialVersionUID = 1L;

	private static final LoginExceptionReason REASON = LoginExceptionReason.INVALID_CREDENTIALS;

	public ApiLoginInvalidException(Exception e) {
		super(REASON, e);
	}

	public ApiLoginInvalidException(LoginAuthenticationException e) {
		super(REASON, e);
	}

	public ApiLoginInvalidException(String message, String encodedData) {
		super(REASON, message, encodedData);
	}

	public ApiLoginInvalidException(String message) {
		super(REASON, message);
	}

	public ApiLoginInvalidException() {
		super(REASON);
	}

}
