package com.dereekb.gae.client.api.model.extension.search.document.response;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientKeyedInvalidAttributeException;
import com.dereekb.gae.client.api.model.extension.search.document.services.ClientTypedModelSearchService;
import com.dereekb.gae.client.api.model.extension.search.shared.response.ClientModelSearchResponse;
import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchServiceResponse;

/**
 * {@link ClientTypedModelSearchService} response
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ClientTypedModelSearchResponse<T>
        extends ClientModelSearchResponse<T>, TypedModelSearchServiceResponse<T> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClientTypedModelSearchResponse<T> performNextSearch()
	        throws UnsupportedOperationException,
	            ClientKeyedInvalidAttributeException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException;

}
