package com.dereekb.gae.model.extension.request.service;

import java.util.List;

import com.dereekb.gae.model.extension.request.Request;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Service for retrieving and deleting {@link Request} instances associated with
 * an object.
 *
 * @author dereekb
 */
public interface RequestService<T> {

	/**
	 * @param object
	 * @return A {@link List} of {@link Request} models associated with the
	 *         object.
	 */
	public List<Request> getAllRequests(T object);

	/**
	 * @param object
	 * @return A {@link List} of {@link ModelKey} for each of the requests
	 *         associated with the object.
	 */
	public List<ModelKey> getAllRequestKeys(T object);

}
