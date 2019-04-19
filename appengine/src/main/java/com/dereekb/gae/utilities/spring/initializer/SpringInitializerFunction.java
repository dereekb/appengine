package com.dereekb.gae.utilities.spring.initializer;

import com.dereekb.gae.utilities.spring.initializer.impl.SpringInitializerImpl;

/**
 * {@link SpringInitializerImpl} delegate.
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
