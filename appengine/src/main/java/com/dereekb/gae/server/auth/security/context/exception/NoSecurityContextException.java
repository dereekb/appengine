package com.dereekb.gae.server.auth.security.context.exception;

import org.springframework.security.core.Authentication;

/**
 * Thrown if there is no security context available, or the
 * {@link Authentication} is null.
 *
 * @author dereekb
 *
 */
public class NoSecurityContextException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
