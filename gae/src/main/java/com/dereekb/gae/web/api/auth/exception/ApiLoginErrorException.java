package com.dereekb.gae.web.api.auth.exception;

import com.dereekb.gae.server.auth.security.login.exception.LoginAuthenticationException;

/**
 * Api error exception.
 *
 * @author dereekb
 *
 */
public class ApiLoginErrorException extends ApiLoginException {

	private static final long serialVersionUID = 1L;

	private static final LoginExceptionReason REASON = LoginExceptionReason.ERROR;

	public ApiLoginErrorException(Exception e) {
		super(REASON, e);
	}

	public ApiLoginErrorException(LoginAuthenticationException e) {
		super(REASON, e);
	}

	public ApiLoginErrorException(String message, String encodedData) {
		super(REASON, message, encodedData);
	}

	public ApiLoginErrorException(String message) {
		super(REASON, message);
	}

	public ApiLoginErrorException() {
		super(REASON);
	}

}
