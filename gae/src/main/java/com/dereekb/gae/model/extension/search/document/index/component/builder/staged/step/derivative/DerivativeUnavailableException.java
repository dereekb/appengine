package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative;


/**
 * @see {@link DerivativeDocumentBuilderDelegate}
 * @author dereekb
 *
 */
public final class DerivativeUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DerivativeUnavailableException() {
		super();
	}

	public DerivativeUnavailableException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DerivativeUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public DerivativeUnavailableException(String message) {
		super(message);
	}

	public DerivativeUnavailableException(Throwable cause) {
		super(cause);
	}

}
