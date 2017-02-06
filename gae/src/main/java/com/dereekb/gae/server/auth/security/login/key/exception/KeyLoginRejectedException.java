package com.dereekb.gae.server.auth.security.login.key.exception;

/**
 * Thrown when the {@link KeyLoginInfo} credentials are rejected by the server.
 *
 * @author dereekb
 *
 */
public class KeyLoginRejectedException extends KeyLoginException {

	private static final long serialVersionUID = 1L;

	public KeyLoginRejectedException() {}

	public KeyLoginRejectedException(String code, String message) {
		super(code, message);
	}

}
