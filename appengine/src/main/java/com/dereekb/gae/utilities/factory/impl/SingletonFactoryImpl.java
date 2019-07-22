package com.dereekb.gae.utilities.factory.impl;

import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * Wrapper for a single value that implements the {@link Factory} interface.
 *
 * @author dereekb
 *
 * @param <T>
 *            Value type
 */
public class SingletonFactoryImpl<T>
        implements Factory<T> {

	private T value;

	public SingletonFactoryImpl(T value) {
		super();
		this.value = value;
	}

	@Override
	public T make() throws FactoryMakeFailureException {
		return this.value;
	}

	public T getValue() {
		return this.value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public static <T> SingletonFactoryImpl<T> withValue(T value) throws NullPointerException {
		if (value == null) {
			throw new NullPointerException("Cannot create SingletonFactory with a null value.");
		}

		SingletonFactoryImpl<T> factory = new SingletonFactoryImpl<T>(value);
		return factory;
	}

}
