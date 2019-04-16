package com.dereekb.gae.utilities.spring.initializer;

/**
 * {@link SpringInitializer} delegate.
 *
 * @author dereekb
 *
 */
public interface SpringInitializerFunction {

	/**
	 * Performs arbitrary initialization of the app.
	 */
	void initializeApplication() throws Exception;

}
