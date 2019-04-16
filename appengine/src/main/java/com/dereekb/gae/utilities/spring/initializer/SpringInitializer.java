package com.dereekb.gae.utilities.spring.initializer;

import org.springframework.beans.factory.InitializingBean;

import com.dereekb.gae.utilities.factory.Factory;

/**
 * Used for initializing the application when created.
 *
 * @author dereekb
 *
 */
public class SpringInitializer
        implements InitializingBean {

	private Factory<SpringInitializerFunction> factory;

	public Factory<SpringInitializerFunction> getFactory() {
		return this.factory;
	}

	public void setFactory(Factory<SpringInitializerFunction> factory) {
		if (factory == null) {
			throw new IllegalArgumentException("factory cannot be null.");
		}

		this.factory = factory;
	}

	// MARK: InitializingBean
	/**
	 * Performs the initialization.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		SpringInitializerFunction delegate = this.factory.make();
		delegate.initializeApplication();
	}

	@Override
	public String toString() {
		return "SpringInitializer [factory=" + this.factory + "]";
	}

}
