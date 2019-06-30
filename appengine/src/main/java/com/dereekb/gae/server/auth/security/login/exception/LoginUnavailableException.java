package com.dereekb.gae.server.auth.security.login.exception;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Thrown when the specified login resource is unavailable.
 *
 * @author dereekb
 *
 */
public class LoginUnavailableException extends LoginException {

	private static final long serialVersionUID = 1L;

	public LoginUnavailableException(ModelKey modelKey) {
		super(modelKey.toString(), "The login with the specified model key was unavialable.");
	}

	public LoginUnavailableException(String username, String message) {
		super(username, message);
	}

	public LoginUnavailableException(String username) {
		super(username);
	}

}
