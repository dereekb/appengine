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
	 * One or more elements failed. This is similar to {@value #EXCEPTION} but
	 * is meant to be captured.
	 */
	ATOMIC_FAILURE,

	/**
	 * An exception occured.
	 */
	EXCEPTION,

	/**
	 * The operation couldn't be completed due to filtering.
	 */
	FILTERED,

	/**
	 * Reason is unspecified.
	 */
	UNSPECIFIED

}
