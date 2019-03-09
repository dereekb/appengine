package com.dereekb.gae.utilities.collections.iterator.index.exception;

import com.dereekb.gae.utilities.collections.iterator.index.IndexedIterator;

/**
 * Thrown by {@link IndexedIterator} implementations when the passed value is an
 * invalid index value.
 *
 * @author dereekb
 *
 */
public class InvalidIteratorIndexException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidIteratorIndexException() {
		super();
	}

	public InvalidIteratorIndexException(String message) {
		super(message);
	}

	public InvalidIteratorIndexException(Throwable cause) {
		super(cause);
	}

}
