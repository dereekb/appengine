package com.dereekb.gae.client.api.model.extension.search;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.model.extension.search.query.service.ModelQueryRequest;
import com.dereekb.gae.model.extension.search.query.service.ModelQueryResponse;
import com.dereekb.gae.model.extension.search.query.service.ModelQueryService;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.model.search.request.SearchRequest;

/**
 * Client query service.
 * 
 * @author dereekb
 * 
 * @see ModelQueryService
 */
public interface ClientQueryService<T extends UniqueModel> {

	/**
	 * Performs a query for models.
	 * 
	 * @param request
	 *            {@link ModelQueryRequest}. Never {@code null}.
	 * @return {@link ModelQueryResponse}. Never {@code null}.
	 * 
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public ModelQueryResponse<T> query(SearchRequest request)
	        throws ClientIllegalArgumentException,
	            ClientRequestFailureException;

}
