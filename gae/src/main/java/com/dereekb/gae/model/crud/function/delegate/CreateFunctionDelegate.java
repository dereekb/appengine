package com.dereekb.gae.model.crud.function.delegate;

import com.dereekb.gae.model.crud.function.exception.InvalidTemplateException;

/**
 * Delegate used in conjunction with a {@link com.dereekb.gae.model.crud.function.CreateFunction} to create a new element.
 * @author dereekb
 *
 * @param <T> Type being created.
 */
public interface CreateFunctionDelegate<T> {

	/**
	 * Creates a new object of type <T> from the given source.
	 *
	 * @param pair
	 * @throw BadCreateTemplateException
	 */
	public T create(T source) throws InvalidTemplateException;

}
