package com.dereekb.gae.utilities.spring.initializer;


/**
 * Used for initializing the spring context.
 *
 * @author dereekb
 *
 */
public interface SpringInitializer {

	/**
	 * Idempotent function that initializes the spring application.
	 *
	 * Should be called after the context is set up.
	 *
	 * @throws Exception thrown if initialization fails.
	 */
	void initializeSpring() throws Exception;

}
