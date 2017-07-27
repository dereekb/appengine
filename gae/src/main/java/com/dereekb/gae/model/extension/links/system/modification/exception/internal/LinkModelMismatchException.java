package com.dereekb.gae.model.extension.links.system.modification.exception.internal;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Thrown in some cases where a specific (same {@link ModelKey})
 * {@link MutableLinkModel} or related type is expected.
 * 
 * @author dereekb
 *
 */
public class LinkModelMismatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LinkModelMismatchException() {
		super();
	}

	public LinkModelMismatchException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LinkModelMismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public LinkModelMismatchException(String message) {
		super(message);
	}

	public LinkModelMismatchException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "LinkModelMismatchException []";
	}

}
