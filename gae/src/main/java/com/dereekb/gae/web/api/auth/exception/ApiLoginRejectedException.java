package com.dereekb.gae.web.api.auth.exception;

/**
 * Rejected login exception.
 *
 * @author dereekb
 *
 */
public class ApiLoginRejectedException extends ApiLoginException {

	private static final long serialVersionUID = 1L;

	private static final LoginExceptionReason REASON = LoginExceptionReason.REJECTED;

	public ApiLoginRejectedException(String message, Throwable cause) {
		super(REASON, message, cause);
	}

	public ApiLoginRejectedException(String message) {
		super(REASON, message);
	}

	public ApiLoginRejectedException(Throwable cause) {
		super(REASON, cause);
	}

	public ApiLoginRejectedException() {
		super(REASON);
	}

}
