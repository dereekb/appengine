package com.dereekb.gae.model.crud.task.impl.delegate;

import com.dereekb.gae.model.crud.function.exception.InvalidTemplateException;
import com.dereekb.gae.model.crud.task.impl.CreatePairTaskImpl;

/**
 * {@link CreatePairTaskImpl} delegate.
 *
 * @author dereekb
 *
 */
public interface CreateTaskDelegate<T> {

	/**
	 * Creates a new element from the source. What values are copied over are up
	 * to the delegate's implementation.
	 *
	 * @param source
	 *            source model. Never {@code null}.
	 * @return new model instance. Never {@code null}.
	 * @throws InvalidTemplateException
	 *             thrown if the template is rejected by the delegate, or could
	 *             not be created.
	 */
	public T createFromSource(T source) throws InvalidTemplateException;

}
