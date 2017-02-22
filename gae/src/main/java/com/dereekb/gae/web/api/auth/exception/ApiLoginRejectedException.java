package com.dereekb.gae.web.api.auth.exception;

import com.dereekb.gae.server.auth.security.login.exception.LoginAuthenticationException;

/**
 * Rejected login exception.
 *
 * @author dereekb
 *
 */
public class ApiLoginRejectedException extends ApiLoginException {

	private static final long serialVersionUID = 1L;

	private static final LoginExceptionReason REASON = LoginExceptionReason.REJECTED;

	public ApiLoginRejectedException(Exception e) {
		super(REASON, e);
	}

	public ApiLoginRejectedException(LoginAuthenticationException e) {
		super(REASON, e);
	}

	public ApiLoginRejectedException(String message, String encodedData) {
		super(REASON, message, encodedData);
	}

	public ApiLoginRejectedException(String message) {
		super(REASON, message);
	}

	public ApiLoginRejectedException() {
		super(REASON);
	}

}
