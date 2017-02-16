package com.dereekb.gae.client.api.model.exception;

import java.util.List;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ClientRequestFailureException} equivalent of
 * {@link AtomicOperationException}.
 * 
 * @author dereekb
 * @see AtomicOperationException
 */
public class ClientAtomicOperationException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	private List<ModelKey> missingKeys;

	public ClientAtomicOperationException(List<ModelKey> missingKeys, ClientApiResponse clientResponse) {
		super(clientResponse);
		this.missingKeys = missingKeys;
	}

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

	public List<ModelKey> getMissingKeys() {
		return this.missingKeys;
	}

	public void setMissingKeys(List<ModelKey> missingKeys) {
		this.missingKeys = missingKeys;
	}

}
