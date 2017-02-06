package com.dereekb.gae.web.api.auth.exception;

/**
 * Api error exception.
 *
 * @author dereekb
 *
 */
public class ApiLoginErrorException extends ApiLoginException {

	private static final long serialVersionUID = 1L;

	private static final LoginExceptionReason REASON = LoginExceptionReason.ERROR;

	public ApiLoginErrorException(String message, Throwable cause) {
		super(REASON, message, cause);
	}

	public ApiLoginErrorException(String message) {
		super(REASON, message);
	}

	public ApiLoginErrorException(Throwable cause) {
		super(REASON, cause);
	}

	public ApiLoginErrorException() {
		super(REASON);
	}

}
