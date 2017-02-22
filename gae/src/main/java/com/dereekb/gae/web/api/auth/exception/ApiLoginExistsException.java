package com.dereekb.gae.web.api.auth.exception;

import com.dereekb.gae.server.auth.security.login.exception.LoginAuthenticationException;

/**
 * Login exists exception.
 *
 * @author dereekb
 *
 */
public class ApiLoginExistsException extends ApiLoginException {

	private static final long serialVersionUID = 1L;

	private static final LoginExceptionReason REASON = LoginExceptionReason.EXISTS;

	public ApiLoginExistsException(Exception e) {
		super(REASON, e);
	}

	public ApiLoginExistsException(LoginAuthenticationException e) {
		super(REASON, e);
	}

	public ApiLoginExistsException(String message, String encodedData) {
		super(REASON, message, encodedData);
	}

	public ApiLoginExistsException(String message) {
		super(REASON, message);
	}

	public ApiLoginExistsException() {
		super(REASON);
	}

}
