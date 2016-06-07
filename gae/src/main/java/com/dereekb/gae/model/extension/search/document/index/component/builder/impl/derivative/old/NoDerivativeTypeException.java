package com.dereekb.gae.model.extension.search.document.index.component.builder.impl.derivative.old;


/**
 * @see {@link IncludedDocumentBuilderDelegate}
 * @author dereekb
 *
 */
public final class NoDerivativeTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoDerivativeTypeException() {
		super();
	}

	public NoDerivativeTypeException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoDerivativeTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoDerivativeTypeException(String message) {
		super(message);
	}

	public NoDerivativeTypeException(Throwable cause) {
		super(cause);
	}

}
