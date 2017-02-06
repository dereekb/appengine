package com.dereekb.gae.web.api.auth.exception;

/**
 * Invalid credentials exception.
 *
 * @author dereekb
 *
 */
public class ApiLoginInvalidException extends ApiLoginException {

	private static final long serialVersionUID = 1L;

	private static final LoginExceptionReason REASON = LoginExceptionReason.INVALID_CREDENTIALS;

	public ApiLoginInvalidException(String message, Throwable cause) {
		super(REASON, message, cause);
	}

	public ApiLoginInvalidException(String message) {
		super(REASON, message);
	}

	public ApiLoginInvalidException(Throwable cause) {
		super(REASON, cause);
	}

	public ApiLoginInvalidException() {
		super(REASON);
	}

}
