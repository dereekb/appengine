package com.dereekb.gae.web.api.exception.resolver;

import com.dereekb.gae.web.api.exception.ApiCaughtRuntimeException;
import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;

public class RuntimeExceptionResolver {

	public static void resolveException(Exception cause) throws RuntimeException {
		Class<?> causeType = cause.getClass();

		// Only throw ApiSafeRuntimeException types.
		if (RuntimeException.class.isAssignableFrom(causeType)) {
			RuntimeException rtCause = (RuntimeException) cause;
			RuntimeExceptionResolver.resolve(rtCause);
		} else {
			throw new RuntimeException(cause);	// Wrap exception and throw.
		}
	}

	public static void resolve(RuntimeException cause) throws RuntimeException {
		if (ApiSafeRuntimeException.class.isAssignableFrom(cause.getClass())) {
			throw cause;
		} else {
			throw ApiCaughtRuntimeException.make(cause);
		}
	}

}
