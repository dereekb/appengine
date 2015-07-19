package com.dereekb.gae.model.extension.links.components.exception;

import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;

/**
 * {@link RuntimeException} thrown when one or more changes performed in
 * {@link LinkModelSet} are determined to be invalid.
 *
 * @author dereekb
 *
 */
public class LinkSaveConditionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LinkSaveConditionException(String message) {
		super(message);
	}

	public LinkSaveConditionException(Throwable cause) {
		super(cause);
	}

}
