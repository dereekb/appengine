package com.dereekb.gae.utilities.collections.iterator.index.exception;

import com.dereekb.gae.utilities.collections.iterator.index.IndexedIterator;

/**
 * Thrown by {@link IndexedIterator} when attempting to retrieve an index that
 * is unavailable.
 *
 * @author dereekb
 *
 */
public class UnavailableIteratorIndexException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnavailableIteratorIndexException() {
		super();
	}

	public UnavailableIteratorIndexException(String message) {
		super(message);
	}

	public UnavailableIteratorIndexException(Throwable cause) {
		super(cause);
	}

}
