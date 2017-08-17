package com.dereekb.gae.utilities.collections.pairs;

/**
 * States for {@link ResultPair}.
 *
 * @author dereekb
 */
public enum ResultsPairState {

	/**
	 * Initial state.
	 */
	NEW,

	/**
	 * State for when a result is created.
	 */
	SUCCESS,

	/**
	 * State for when no result could be generated.
	 */
	FAILURE,

	/**
	 * State for when a successful result was cleared (and set to nothing)
	 * before being returned.
	 */
	CLEARED,

	/**
	 * State for when a successful result was replaced by another (non-null
	 * value) before being returned.
	 */
	REPLACED

}
