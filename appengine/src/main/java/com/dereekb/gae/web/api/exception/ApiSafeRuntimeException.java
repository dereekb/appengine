package com.dereekb.gae.web.api.exception;

/**
 * {@link RuntimeException} extension that also implements
 * {@link ApiSafeRuntimeException}, allowing the exceptions to
 * 
 * @author dereekb
 *
 */
public abstract class ApiSafeRuntimeException extends RuntimeException
        implements ApiResponseErrorConvertable {

	private static final long serialVersionUID = 1L;

	public ApiSafeRuntimeException() {
		super();
	}

	public ApiSafeRuntimeException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApiSafeRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiSafeRuntimeException(String message) {
		super(message);
	}

	public ApiSafeRuntimeException(Throwable cause) {
		super(cause);
	}

}
