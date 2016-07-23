package com.dereekb.gae.web.api.auth.exception;

import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Exception thrown by API Login instances.
 *
 * @author dereekb
 *
 */
public class ApiLoginException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static enum LoginExceptionReason {

		UNAVAILABLE("LOGIN_UNAVALABLE", "Login Unavailble"),

		INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Invalid Credentials"),

		EXISTS("LOGIN_EXISTS", "Login Exists"),

		UNSUPPORTED("LOGIN_UNSUPPORTED", "Unsupported type"),

		ERROR("LOGIN_ERROR", "Unexpected error");

		private final String code;
		private final String title;

		private LoginExceptionReason(String code, String title) {
			this.code = code;
			this.title = title;
		}

		public ApiResponseErrorImpl makeResponseError() {
			ApiResponseErrorImpl error = new ApiResponseErrorImpl();

			error.setCode(this.code);
			error.setTitle(this.title);

			return error;
		}

	}

	private LoginExceptionReason reason;

	public ApiLoginException(LoginExceptionReason reason) {
		this(reason, null, null);
	}

	public ApiLoginException(LoginExceptionReason reason, String message) {
		this(reason, message, null);
	}

	public ApiLoginException(LoginExceptionReason reason, Throwable cause) {
		this(reason, null, cause);
	}

	public ApiLoginException(LoginExceptionReason reason, String message, Throwable cause) {
		super(message, cause);
		this.setReason(reason);
	}

	public LoginExceptionReason getReason() {
		return this.reason;
	}

	public void setReason(LoginExceptionReason reason) {
		this.reason = reason;
	}

}
