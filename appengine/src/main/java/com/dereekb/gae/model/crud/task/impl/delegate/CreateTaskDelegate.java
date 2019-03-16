package com.dereekb.gae.model.crud.task.impl.delegate;

import com.dereekb.gae.model.crud.task.impl.CreateTaskImpl;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link CreateTaskImpl} delegate.
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
	 * @throws InvalidAttributeException
	 *             thrown if the template has an invalid attribute.
	 */
	public T createFromSource(T source) throws InvalidAttributeException;

}
