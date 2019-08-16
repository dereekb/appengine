package com.dereekb.gae.client.api.model.extension.search.document.services;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientKeyedInvalidAttributeException;
import com.dereekb.gae.client.api.model.extension.search.document.response.ClientTypedModelSearchResponse;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.extension.search.document.service.ModelSearchService;
import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchService;
import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchServiceRequest;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Client document search service.
 *
 * @author dereekb
 *
 * @see ModelSearchService
 */
public interface ClientTypedModelSearchService<T extends UniqueModel>
        extends TypedModelSearchService<T> {

	/**
	 * Performs a search for models using default security.
	 *
	 * @param request
	 *            {@link TypedModelSearchServiceRequest}. Never {@code null}.
	 * @return {@link ClientTypedModelSearchResponse}. Never {@code null}.
	 *
	 * @throws ClientKeyedInvalidAttributeException
	 *             thrown if one or more query attributes are invalid.
	 * @throws ClientIllegalArgumentException
	 *             thrown if the request has an illegal argument.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public ClientTypedModelSearchResponse<T> search(TypedModelSearchServiceRequest request)
	        throws ClientKeyedInvalidAttributeException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException;

	/**
	 * Performs a search for models.
	 *
	 * @param request
	 *            {@link TypedModelSearchServiceRequest}. Never {@code null}.
	 * @param security
	 *            {@link ClientRequestSecurity}, or {@code null}.
	 * @return {@link ClientTypedModelSearchResponse}. Never {@code null}.
	 *
	 * @throws ClientKeyedInvalidAttributeException
	 *             thrown if one or more query attributes are invalid.
	 * @throws ClientIllegalArgumentException
	 *             thrown if the request has an illegal argument.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public ClientTypedModelSearchResponse<T> search(TypedModelSearchServiceRequest request,
	                                          ClientRequestSecurity security)
	        throws ClientKeyedInvalidAttributeException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException;

}
