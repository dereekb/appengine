package com.dereekb.gae.model.extension.links.system.modification.exception;

/**
 * Thrown when a single model has more than one changes queue'd for a single
 * link.
 * 
 * @author dereekb
 *
 */
public class MultipleLinkModificationQueueException extends InvalidLinkModificationSystemRequestException {

	private static final long serialVersionUID = 1L;

	public MultipleLinkModificationQueueException() {
		super();
	}

	public MultipleLinkModificationQueueException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MultipleLinkModificationQueueException(String message, Throwable cause) {
		super(message, cause);
	}

	public MultipleLinkModificationQueueException(String message) {
		super(message);
	}

	public MultipleLinkModificationQueueException(Throwable cause) {
		super(cause);
	}

}
