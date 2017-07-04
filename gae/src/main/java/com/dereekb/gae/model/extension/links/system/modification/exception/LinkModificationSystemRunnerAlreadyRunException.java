package com.dereekb.gae.model.extension.links.system.modification.exception;

public class LinkModificationSystemRunnerAlreadyRunException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LinkModificationSystemRunnerAlreadyRunException() {
		super();
	}

	public LinkModificationSystemRunnerAlreadyRunException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LinkModificationSystemRunnerAlreadyRunException(String message, Throwable cause) {
		super(message, cause);
	}

	public LinkModificationSystemRunnerAlreadyRunException(String message) {
		super(message);
	}

	public LinkModificationSystemRunnerAlreadyRunException(Throwable cause) {
		super(cause);
	}

}
