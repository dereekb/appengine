package com.dereekb.gae.utilities.factory;

import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * Factory interface for creating new instances of the given type.
 *
 * @author dereekb
 *
 * @param <T>
 *            Output type
 */
public interface Factory<T> {

	/**
	 * Makes a new instance of the model.
	 *
	 * @return Model. May be {@code null} in some cases.
	 * @throws FactoryMakeFailureException
	 *             thrown if the model cannot be created.
	 */
	public T make() throws FactoryMakeFailureException;

}
