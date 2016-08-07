package com.dereekb.gae.web.api.auth.exception;

/**
 * Login exists exception.
 *
 * @author dereekb
 *
 */
public class ApiLoginExistsException extends ApiLoginException {

	private static final long serialVersionUID = 1L;

	private static final LoginExceptionReason REASON = LoginExceptionReason.EXISTS;

	public ApiLoginExistsException(String message, Throwable cause) {
		super(REASON, message, cause);
	}

	public ApiLoginExistsException(String message) {
		super(REASON, message);
	}

	public ApiLoginExistsException(Throwable cause) {
		super(REASON, cause);
	}

	public ApiLoginExistsException() {
		super(REASON);
	}

}
