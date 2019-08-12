package com.dereekb.gae.model.extension.search.document.service.exception;

import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

/**
 * {@link IllegalQueryArgumentException} thrown when the index is not provided.
 *
 * @author dereekb
 *
 */
public class SearchIndexRequiredException extends IllegalQueryArgumentException {

	private static final long serialVersionUID = 1L;

	public SearchIndexRequiredException() {
		super("The index is required.");
	}

	public SearchIndexRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public SearchIndexRequiredException(String s) {
		super(s);
	}

	public SearchIndexRequiredException(Throwable cause) {
		super(cause);
	}

}
