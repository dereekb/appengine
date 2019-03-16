package com.dereekb.gae.model.extension.links.system.modification.exception.internal;

/**
 * Exception thrown when submitted changes fail due to an unexpected reason.
 * 
 * @author dereekb
 *
 */
public class UnexpectedLinkModificationSystemChangeException extends InternalLinkModificationSystemException {

	private static final long serialVersionUID = 1L;

	public UnexpectedLinkModificationSystemChangeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnexpectedLinkModificationSystemChangeException(Throwable cause) {
		super(cause);
	}

}
