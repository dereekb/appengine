package com.dereekb.gae.client.api.model.exception;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;

/**
 * {@link ClientRequestFailureException} equivalent of
 * {@link AtomicOperationException}.
 * 
 * @author dereekb
 * @see AtomicOperationException
 */
public class ClientAtomicOperationException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	public ClientAtomicOperationException() {}

	public ClientAtomicOperationException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientAtomicOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientAtomicOperationException(String message) {
		super(message);
	}

	public ClientAtomicOperationException(Throwable cause) {
		super(cause);
	}

}
