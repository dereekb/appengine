package com.dereekb.gae.utilities.factory;

import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * {@link Factory} extension that creates with a configuration.
 * 
 * @author dereekb
 *
 * @param <C>
 *            configuration type
 * @param <T>
 *            output type
 */
public interface ConfigurableFactory<C, T>
        extends Factory<T> {

	public T make(C configuration) throws FactoryMakeFailureException;

}
