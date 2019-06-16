package com.dereekb.gae.model.extension.search.document.index.service.exception;

import com.dereekb.gae.model.extension.deprecated.search.document.index.service.TypedDocumentIndexService;

/**
 * Used by {@link TypedDocumentIndexService} for instances where a requested
 * type was not available.
 *
 * @author dereekb
 *
 */
public class UnregisteredSearchTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnregisteredSearchTypeException() {
		super();
	}

	public UnregisteredSearchTypeException(String message) {
		super(message);
	}

}
