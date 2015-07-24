package com.dereekb.gae.web.api.model.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Delegate for {@link ReadModelController}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface ReadModelControllerDelegate<T extends UniqueModel> {

	/**
	 * Reads models in the {@link ReadRequest}.
	 *
	 * @param readRequest
	 *            {@link ReadRequest}. Never {@code null}.
	 * @return {@link ReadResponse}. Never {@code null}.
	 * @throws AtomicOperationException
	 */
	public ReadResponse<T> read(ReadRequest<T> readRequest) throws AtomicOperationException;

	/**
	 * Retrieves the included values for the model.
	 *
	 * @param models
	 *            Models to retrieve related values for.
	 * @param relatedFilter
	 *            Optional inclusive filter. Values in the {@link Set} are
	 *            included. If {@code null}, this will read all available
	 *            included models.
	 * @return
	 */
	public Map<String, Object> readIncluded(Collection<T> models,
	                                        Set<String> relatedFilter);

}
