package com.dereekb.gae.web.api.exception.resolver;

import com.dereekb.gae.web.api.exception.ApiCaughtRuntimeException;
import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;

public class RuntimeExceptionResolver {

	public static void resolve(RuntimeException cause) throws RuntimeException {
		if (ApiSafeRuntimeException.class.isAssignableFrom(cause.getClass())) {
			throw cause;
		} else {
			throw ApiCaughtRuntimeException.make(cause);
		}
	}

}
