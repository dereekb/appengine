package com.dereekb.gae.model.extension.links.system.modification.exception.internal;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstance;

/**
 * Thrown when a {@link LinkModificationSystemInstance} has already been run.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemInstanceAlreadyRunException extends InternalLinkModificationSystemException {

	private static final long serialVersionUID = 1L;

	public LinkModificationSystemInstanceAlreadyRunException() {
		super();
	}

	public LinkModificationSystemInstanceAlreadyRunException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LinkModificationSystemInstanceAlreadyRunException(String message, Throwable cause) {
		super(message, cause);
	}

	public LinkModificationSystemInstanceAlreadyRunException(String message) {
		super(message);
	}

	public LinkModificationSystemInstanceAlreadyRunException(Throwable cause) {
		super(cause);
	}

}
