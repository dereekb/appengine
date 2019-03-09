package com.dereekb.gae.model.extension.request.editor;

import com.dereekb.gae.model.extension.deprecated.request.service.RequestService;

/**
 * Used to create and delete similar {@link Request} for an element.
 *
 * @author dereekb
 * @see RequestService
 */
public interface RequestEditor<T> {

	/**
	 * Creates one or more {@link Request} for the input objects if no request
	 * currently exists.
	 *
	 * The requests should be saved and available to be looked up if this
	 * function completes.
	 *
	 * @param objects
	 *            Objects to delete requests from.
	 * @throws RequestChangeException
	 *             Thrown when one or more objects failed to generate requests.
	 */
	public void makeRequests(Iterable<T> objects) throws RequestChangeException;

	/**
	 * Deletes one or more {@link Request} instances from the input objects if
	 * they exist.
	 *
	 * @param objects
	 *            Objects to delete requests from.
	 * @throws RequestChangeException
	 *             Thrown when one or more objects failed to delete their
	 *             requests.
	 */
	public void deleteRequests(Iterable<T> objects) throws RequestChangeException;

}
