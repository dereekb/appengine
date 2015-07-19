package com.dereekb.gae.model.extension.links.components.exception;

import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;

/**
 * Generic {@link RuntimeException} thrown when a {@link LinkModelSet} is being
 * saved.
 *
 * @author dereekb
 *
 */
public class LinkSaveException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LinkSaveException(String message, Throwable cause) {
		super(message, cause);
	}

	public LinkSaveException(String message) {
		super(message);
	}

	public LinkSaveException(Throwable cause) {
		super(cause);
	}

}
