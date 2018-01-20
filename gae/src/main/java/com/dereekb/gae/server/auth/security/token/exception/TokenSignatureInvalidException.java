package com.dereekb.gae.server.auth.security.token.exception;

/**
 * Thrown when a token's signature in the request is invalid.
 *
 * @author dereekb
 *
 */
public class TokenSignatureInvalidException extends TokenException {

	private static final long serialVersionUID = 1L;

	public TokenSignatureInvalidException() {
		super(TokenExceptionReason.INVALID_SIGNATURE);
	}

	public TokenSignatureInvalidException(Throwable cause) {
		super(TokenExceptionReason.INVALID_SIGNATURE, cause);
	}

	public TokenSignatureInvalidException(String message) {
		super(TokenExceptionReason.INVALID_SIGNATURE, message);
	}

	public TokenSignatureInvalidException(String message, Throwable cause) {
		super(TokenExceptionReason.INVALID_SIGNATURE, message, cause);
	}

}
