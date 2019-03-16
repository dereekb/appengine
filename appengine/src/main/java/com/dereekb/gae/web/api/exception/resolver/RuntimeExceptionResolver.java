package com.dereekb.gae.web.api.exception.resolver;

import com.dereekb.gae.web.api.exception.ApiCaughtRuntimeException;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
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
		Class<?> causeClass = cause.getClass();

		if (ApiSafeRuntimeException.class.isAssignableFrom(causeClass)) {
			throw cause;
		} else if (IllegalArgumentException.class.isAssignableFrom(causeClass)) {
			throw new ApiIllegalArgumentException((IllegalArgumentException) cause);
		} else {
			throw ApiCaughtRuntimeException.make(cause);
		}
	}

}
