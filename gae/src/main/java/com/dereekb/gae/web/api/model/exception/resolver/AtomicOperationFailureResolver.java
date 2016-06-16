package com.dereekb.gae.web.api.model.exception.resolver;

import java.util.List;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;

/**
 * Is the API equivalent of {@link AtomicOperationException}.
 *
 * @author dereekb
 *
 */
public class AtomicOperationFailureResolver {

	/**
	 * Will throw a new exception based on the input atomic operation exception.
	 *
	 * @param e
	 * @throws RuntimeException
	 */
	public static void resolve(AtomicOperationException e) throws RuntimeException {
		AtomicOperationExceptionReason reason = e.getReason();

		switch (reason) {
			case EXCEPTION:
				throw e;
			case UNSPECIFIED:
			case UNAVAILABLE:
				List<String> unavailable = e.getUnavailableStringKeys();
				throw new MissingRequiredResourceException(unavailable);
			default:
				throw new RuntimeException("Could not resolve AtomicOperationException.");
		}

	}

}
