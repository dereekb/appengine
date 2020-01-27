package com.dereekb.gae.web.api.auth.exception;

import com.dereekb.gae.server.auth.security.login.exception.LoginAuthenticationException;
import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Exception thrown by API Login instances.
 *
 * @author dereekb
 *
 */
public class ApiLoginException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	public static enum LoginExceptionReason {

	    // Bad Request
		UNAVAILABLE("LOGIN_UNAVALABLE", "Login Unavailble"),

		UNSUPPORTED("LOGIN_UNSUPPORTED", "Unsupported type"),

	    // Conflict
		EXISTS("LOGIN_EXISTS", "Login Exists"),

	    // Conflict
		DISABLED("LOGIN_DISABLED", "Login Disabled"),

	    // Internal Service Error
		ERROR("LOGIN_ERROR", "Unexpected error"),

	    // Forbidden
		INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Invalid Credentials"),

		EXPIRED_CREDENTIALS("EXPIRED_CREDENTIALS", "Expired Credentials"),

		REJECTED("REJECTED", "Rejected Credentials");

		private final String code;
		private final String title;

		private LoginExceptionReason(String code, String title) {
			this.code = code;
			this.title = title;
		}

		public ApiResponseErrorImpl makeResponseError() {
			ApiResponseErrorImpl error = new ApiResponseErrorImpl(this.code);

			error.setTitle(this.title);

			return error;
		}

	}

	private LoginExceptionReason reason;
	private String encodedData;

	public ApiLoginException(LoginExceptionReason reason) {
		this(reason, null, null);
	}

	public ApiLoginException(LoginExceptionReason reason, String message) {
		this(reason, message, null);
	}

	public ApiLoginException(LoginExceptionReason reason, String message, String encodedData) {
		super(message);
		this.setEncodedData(encodedData);
		this.setReason(reason);
	}

	public ApiLoginException(LoginExceptionReason reason, LoginAuthenticationException e) {
		super(e.getMessage(), e);
		this.setEncodedData(e.getEncodedData());
		this.setReason(reason);
	}

	public ApiLoginException(LoginExceptionReason reason, Exception e) {
		super(e.getMessage(), e);
		this.setReason(reason);
	}

	public LoginExceptionReason getReason() {
		return this.reason;
	}

	public void setReason(LoginExceptionReason reason) {
		if (reason == null) {
			throw new IllegalArgumentException("Reason cannot be null.");
		}

		this.reason = reason;
	}

	public String getEncodedData() {
		return this.encodedData;
	}

	public void setEncodedData(String encodedData) {
		this.encodedData = encodedData;
	}

	@Override
	public ApiResponseError asResponseError() {
		LoginExceptionReason reason = this.getReason();
		ApiResponseErrorImpl error = reason.makeResponseError();

		error.setData(this.getEncodedData());
		error.setDetail(this.getMessage());

		return error;
	}

}
