package com.dereekb.gae.server.auth.security.token.exception;

import org.springframework.security.core.AuthenticationException;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Exception related to {@link LoginToken} objects.
 *
 * @author dereekb
 *
 */
public class TokenException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public static enum TokenExceptionReason {

	    // Unauthorized
		MISSING_TOKEN("MISSING_AUTHORIZATION", "Missing Authorization"),

		EXPIRED_TOKEN("EXPIRED_AUTHORIZATION", "Expired Authorization"),

		INVALID_TOKEN("INVALID_AUTHORIZATION", "Invalid Authorization");

		private final String code;
		private final String title;

		private TokenExceptionReason(String code, String title) {
			this.code = code;
			this.title = title;
		}

		public ApiResponseErrorImpl makeResponseError() {
			ApiResponseErrorImpl error = new ApiResponseErrorImpl(this.code);

			error.setTitle(this.title);

			return error;
		}

	}

	private TokenExceptionReason reason;

	public TokenException(TokenExceptionReason reason) {
		this(reason, null, null);
	}

	public TokenException(TokenExceptionReason reason, String message) {
		this(reason, message, null);
	}

	public TokenException(TokenExceptionReason reason, Throwable cause) {
		this(reason, null, cause);
	}

	public TokenException(TokenExceptionReason reason, String message, Throwable cause) {
		super(message, cause);
		this.setReason(reason);
	}

	public TokenExceptionReason getReason() {
		return this.reason;
	}

	public void setReason(TokenExceptionReason reason) {
		this.reason = reason;
	}

}
