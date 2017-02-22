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

	public KeyLoginException(String message, String encodedData) {
		super(message, encodedData);
	}

	public KeyLoginException(String message) {
		super(message);
	}

}
