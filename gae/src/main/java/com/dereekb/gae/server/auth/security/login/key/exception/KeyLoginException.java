package com.dereekb.gae.server.auth.security.login.key.exception;

import com.dereekb.gae.server.auth.security.login.exception.LoginAuthenticationException;

/**
 * General KeyLoginException.
 *
 * @author dereekb
 *
 */
public class KeyLoginException extends LoginAuthenticationException {

	private static final long serialVersionUID = 1L;

	public KeyLoginException() {
		super();
	}

	public KeyLoginException(String code, String message) {
		super(code, message);
	}

	public KeyLoginException(String message) {
		super(message);
	}

	public KeyLoginException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "KeyLoginException [code=" + this.code + ", getMessage()=" + this.getMessage() + "]";
	}

}
