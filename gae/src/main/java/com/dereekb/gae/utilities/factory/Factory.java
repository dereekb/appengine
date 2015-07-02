package com.dereekb.gae.utilities.factory;

/**
 * Factory interface for creating new instances of the given type.
 * 
 * @author dereekb
 *
 * @param <T> Output type
 */
public interface Factory<T> {

	public T make() throws FactoryMakeFailureException;

}
