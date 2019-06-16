package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative;


/**
 * @see {@link IncludedDocumentBuilderDelegate}
 * @author dereekb
 *
 */
public final class IncludedModelUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IncludedModelUnavailableException() {
		super();
	}

	public IncludedModelUnavailableException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IncludedModelUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncludedModelUnavailableException(String message) {
		super(message);
	}

	public IncludedModelUnavailableException(Throwable cause) {
		super(cause);
	}

}
