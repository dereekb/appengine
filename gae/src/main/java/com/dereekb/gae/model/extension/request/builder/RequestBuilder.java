package com.dereekb.gae.model.extension.request.builder;

import com.dereekb.gae.model.extension.request.Request;

/**
 * Used to generate a new {@link Request} for the input model.
 *
 * @author dereekb
 *
 * @param <T>
 *            Input type.
 */
public interface RequestBuilder<T> {

	/**
	 * Generates a new {@link Request}. The request is not saved.
	 *
	 * @param object
	 *            Input object.
	 * @return A new {@link Request} for the input object.
	 */
	public Request buildRequest(T object);

}
