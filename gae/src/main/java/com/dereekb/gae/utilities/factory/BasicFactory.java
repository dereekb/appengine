package com.dereekb.gae.utilities.factory;

/**
 * Builds new models
 * 
 * @author dereekb
 *
 * @param <T>
 */
public class BasicFactory<T>
        implements Factory<T> {

	private final Class<T> type;

	public BasicFactory(Class<T> type) {
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
