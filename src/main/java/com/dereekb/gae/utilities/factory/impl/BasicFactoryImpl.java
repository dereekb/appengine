package com.dereekb.gae.utilities.factory.impl;

import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * Builds new models
 * 
 * @author dereekb
 *
 * @param <T>
 */
public class BasicFactoryImpl<T>
        implements Factory<T> {

	private final Class<T> type;

	public BasicFactoryImpl(Class<T> type) {
		this.type = type;
	}

	public T make() throws FactoryMakeFailureException {
		T object;

		try {
			object = this.makeObject();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new FactoryMakeFailureException();
		}

		return object;
	}

	public T makeObject() throws InstantiationException, IllegalAccessException {
		T instance = this.type.newInstance();
		return instance;
	}

}
