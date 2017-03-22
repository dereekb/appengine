package com.dereekb.gae.client.api.model.extension.search.query.services;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.extension.search.query.response.ClientModelQueryResponse;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
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
	 * Performs a query for models using default security.
	 * 
	 * @param request
	 *            {@link ModelQueryRequest}. Never {@code null}.
	 * @return {@link ModelQueryResponse}. Never {@code null}.
	 * 
	 * @throws ClientIllegalArgumentException
	 *             thrown if the request has an illegal argument.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public ClientModelQueryResponse<T> query(SearchRequest request)
	        throws ClientIllegalArgumentException,
	            ClientRequestFailureException;

	/**
	 * Performs a query for models.
	 * 
	 * @param request
	 *            {@link ModelQueryRequest}. Never {@code null}.
	 * @param security
	 *            {@link ClientRequestSecurity}, or {@code null}.
	 * @return {@link ModelQueryResponse}. Never {@code null}.
	 * 
	 * @throws ClientIllegalArgumentException
	 *             thrown if the request has an illegal argument.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public ClientModelQueryResponse<T> query(SearchRequest request,
	                                         ClientRequestSecurity security)
	        throws ClientIllegalArgumentException,
	            ClientRequestFailureException;

}
