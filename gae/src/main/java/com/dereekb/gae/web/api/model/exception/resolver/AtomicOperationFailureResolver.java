package com.dereekb.gae.web.api.model.exception.resolver;

import java.util.List;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;

/**
 * Used to resolve a {@link AtomicOperationException} by throwing a new
 * exception for the system to catch.
 *
 * @author dereekb
 *
 */
public class AtomicOperationFailureResolver {

	/**
	 * Will throw a new exception based on the input atomic operation exception.
	 *
	 * @param e
	 *            {@link AtomicOperationException}. Never {@code null}.
	 * @throws RuntimeException
	 *             thrown if the input type is
	 *             {@link AtomicOperationExceptionReason#EXCEPTION}.
	 */
	public static void resolve(AtomicOperationException e) throws RuntimeException {
		AtomicOperationExceptionReason reason = e.getReason();

		switch (reason) {
			case EXCEPTION:
				Throwable cause = e.getException();
				Class<?> causeType = cause.getClass();

				// Only throw ApiSafeRuntimeException types.
				if (RuntimeException.class.isAssignableFrom(causeType.getClass())) {
					RuntimeException rtCause = (RuntimeException) cause;
					RuntimeExceptionResolver.resolve(rtCause);
				} else {
					throw e;
				}
			case UNSPECIFIED:
			case UNAVAILABLE:
				List<String> unavailable = e.getUnavailableStringKeys();
				throw new MissingRequiredResourceException(unavailable);
			default:
				throw new RuntimeException("Could not resolve AtomicOperationException.");
		}

	}

}
