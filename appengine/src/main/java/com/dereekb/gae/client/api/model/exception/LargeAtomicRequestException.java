package com.dereekb.gae.client.api.model.exception;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;

/**
 * {@link ClientRequestFailureException} for atomic requests that attempts to request too many models.
 *
 * @author dereekb
 */
public class LargeAtomicRequestException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	public LargeAtomicRequestException() {}

	public LargeAtomicRequestException(String message) {
		super(message);
	}

	public LargeAtomicRequestException(Throwable cause) {
		super(cause);
	}


}
