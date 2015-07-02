package com.dereekb.gae.model.crud.services.exception;

/**
 * Reasons for an {@link AtomicOperationException} being thrown.
 *
 * @author dereekb
 */
public enum AtomicOperationExceptionReason {

	/**
	 * Required objects were unavailable.
	 */
	UNAVAILABLE,

	/**
	 * An exception occured.
	 */
	EXCEPTION

}
