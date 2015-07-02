package com.dereekb.gae.model.extension.request.reader;

import com.dereekb.gae.model.extension.request.Request;

/**
 * Used to look up a {@link Request} for input models.
 *
 * The {@link RequestReader} is pre-configured to lookup a certain type of
 * request.
 *
 * @author dereekb
 *
 * @param <T>
 *            Input type.
 */
public interface RequestReader<T> {

	/**
	 * @param object
	 *            Input object.
	 * @return A {@link Request} instance linked to the model, if it exists.
	 *         {@link Null} if it doesn't.
	 */
	public Request readRequest(T object);

	/**
	 * @param object
	 * @return True if this object already has a request.
	 */
	public boolean hasRequest(T object);

}
