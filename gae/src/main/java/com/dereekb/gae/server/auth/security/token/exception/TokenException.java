package com.dereekb.gae.server.auth.security.token.exception;

import org.springframework.security.core.AuthenticationException;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * Exception related to {@link LoginToken} objects.
 *
 * @author dereekb
 *
 */
public class TokenException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public TokenException(String message) {
		super(message);
	}

}
