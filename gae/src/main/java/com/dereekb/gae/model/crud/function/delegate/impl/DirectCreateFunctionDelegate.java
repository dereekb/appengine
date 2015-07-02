package com.dereekb.gae.model.crud.function.delegate.impl;

import com.dereekb.gae.model.crud.function.delegate.CreateFunctionDelegate;


/**
 * Implementation of the {@link CreateFunctionDelegate} interface that forwards
 * the source as the created object.
 *
 * Useful for testing cases, but not recommended since there can be side effects
 * if the source model already has an identifier, and is reused.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class DirectCreateFunctionDelegate<T>
        implements CreateFunctionDelegate<T> {

	@Override
	public T create(T source) {
		return source;
	}

}
