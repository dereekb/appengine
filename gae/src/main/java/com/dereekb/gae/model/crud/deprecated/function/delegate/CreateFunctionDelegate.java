package com.dereekb.gae.model.crud.deprecated.function.delegate;

import com.dereekb.gae.model.crud.exception.InvalidTemplateException;

/**
 * Delegate used in conjunction with a {@link com.dereekb.gae.model.crud.deprecated.function.CreateFunction} to create a new element.
 * @author dereekb
 *
 * @param <T> Type being created.
 */
@Deprecated
public interface CreateFunctionDelegate<T> {

	/**
	 * Creates a new object of type <T> from the given source.
	 *
	 * @param pair
	 * @throw BadCreateTemplateException
	 */
	public T create(T source) throws InvalidTemplateException;

}
