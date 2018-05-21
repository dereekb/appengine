package com.dereekb.gae.model.crud.services.components;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Thread-safe service for reading existing objects.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ReadService<T> {

	/**
	 * Checks which models exist.
	 *
	 * @param request
	 *            {@link ReadRequest}. Never {@code null}.
	 * @return {@link ReadResponse}. Never {@code null}.
	 * @throws AtomicOperationException
	 *             Occurs when the request specifies "atomic" and not all
	 *             objects requested exist.
	 */
	public ReadResponse<ModelKey> exists(ReadRequest request) throws AtomicOperationException;

	/**
	 * Reads objects using the input request.
	 *
	 * @param request
	 *            {@link ReadRequest}. Never {@code null}.
	 * @return {@link ReadResponse}. Never {@code null}.
	 * @throws AtomicOperationException
	 *             Occurs when the request specifies "atomic" and not all
	 *             objects requested can be read.
	 */
	public ReadResponse<T> read(ReadRequest request) throws AtomicOperationException;

}
