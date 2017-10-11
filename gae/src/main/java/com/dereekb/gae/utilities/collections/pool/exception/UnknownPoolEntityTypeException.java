package com.dereekb.gae.utilities.collections.pool.exception;

import com.dereekb.gae.utilities.collections.pool.MatchPool;

/**
 * Optional {@link RuntimeException} thrown by {@link MatchPool} for guranteed unknown
 * entity types.
 * 
 * @author dereekb
 *
 */
public class UnknownPoolEntityTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnknownPoolEntityTypeException() {
		super();
	}

	public UnknownPoolEntityTypeException(String message) {
		super(message);
	}

	public UnknownPoolEntityTypeException(Throwable cause) {
		super(cause);
	}

}
