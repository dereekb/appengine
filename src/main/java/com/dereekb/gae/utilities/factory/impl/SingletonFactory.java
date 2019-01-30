package com.dereekb.gae.utilities.factory.impl;

import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * Wrapper for a single value that implements the {@link Factory} interface.
 * 
 * @author dereekb
 *
 * @param <T> Value type
 */
public class SingletonFactory<T>
        implements Factory<T> {

	private T value;

	private SingletonFactory(T value) {
		super();
		this.value = value;
	}

	@Override
	public T make() throws FactoryMakeFailureException {
		return value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) throws NullPointerException {

		this.value = value;
	}

	public static <T> SingletonFactory<T> withValue(T value) throws NullPointerException {
		if (value == null) {
			throw new NullPointerException("Cannot create SingletonFactory with a null value.");
		}

		SingletonFactory<T> factory = new SingletonFactory<T>(value);
		return factory;
	}

}
