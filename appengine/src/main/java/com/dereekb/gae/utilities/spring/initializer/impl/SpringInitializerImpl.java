package com.dereekb.gae.utilities.spring.initializer.impl;

import org.springframework.beans.factory.InitializingBean;

import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.spring.initializer.SpringInitializer;
import com.dereekb.gae.utilities.spring.initializer.SpringInitializerFunction;

/**
 * {@link SpringInitializer} implementation.
 *
 * @author dereekb
 *
 */
public class SpringInitializerImpl
        implements SpringInitializer, InitializingBean {

	private boolean initializeWithSpring = true;
	private Factory<SpringInitializerFunction> factory;

	public SpringInitializerImpl(Factory<SpringInitializerFunction> factory) {
		super();
		this.setFactory(factory);
	}

	public boolean isInitializeWithSpring() {
		return this.initializeWithSpring;
	}

	public void setInitializeWithSpring(boolean initializeWithSpring) {
		this.initializeWithSpring = initializeWithSpring;
	}

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
		if (this.initializeWithSpring) {
			this.initializeSpring();
		}
	}

	// MARK: SpringInitializerÏ
	@Override
	public void initializeSpring() throws Exception {
		SpringInitializerFunction delegate = this.factory.make();

		if (delegate != null) {
			delegate.initializeApplication();
		}
	}

	@Override
	public String toString() {
		return "SpringInitializer [factory=" + this.factory + "]";
	}

}
