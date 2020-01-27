package com.dereekb.gae.web.api.auth.exception;

import com.dereekb.gae.server.auth.security.login.exception.LoginAuthenticationException;

/**
 * Login disabled exception.
 *
 * @author dereekb
 *
 */
public class ApiLoginDisabledException extends ApiLoginException {

	private static final long serialVersionUID = 1L;

	private static final LoginExceptionReason REASON = LoginExceptionReason.DISABLED;

	public ApiLoginDisabledException(Exception e) {
		super(REASON, e);
	}

	public ApiLoginDisabledException(LoginAuthenticationException e) {
		super(REASON, e);
	}

	public ApiLoginDisabledException(String message, String encodedData) {
		super(REASON, message, encodedData);
	}

	public ApiLoginDisabledException(String message) {
		super(REASON, message);
	}

	public ApiLoginDisabledException() {
		super(REASON);
	}

}
