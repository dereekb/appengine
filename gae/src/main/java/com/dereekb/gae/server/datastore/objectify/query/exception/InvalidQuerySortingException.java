package com.dereekb.gae.server.datastore.objectify.query.exception;

import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

/**
 * Thrown by a query during configuration if the sorting configuration isn't
 * correct.
 * <p>
 * Typically this is when the first/primary field isn't filtered on before
 * another field.
 * 
 * @author dereekb
 *
 */
public class InvalidQuerySortingException extends IllegalQueryArgumentException {

	private static final long serialVersionUID = 1L;

	public InvalidQuerySortingException() {
		super();
	}

	public InvalidQuerySortingException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidQuerySortingException(String s) {
		super(s);
	}

	public InvalidQuerySortingException(Throwable cause) {
		super(cause);
	}

}
