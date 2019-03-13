package com.dereekb.gae.client.api.model.extension.search.query.response;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientKeyedInvalidAttributeException;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link SerializedClientApiResponse} extension for read requests.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface SerializedClientModelQueryApiResponse<T extends UniqueModel>
        extends SerializedClientApiResponse<ClientModelQueryResponse<T>> {

	/**
	 * {@inheritDoc}
	 *
	 * @throws ClientKeyedInvalidAttributeException
	 *             thrown if one or more query attributes are invalid.
	 */
	@Override
	public ClientModelQueryResponse<T> getSerializedResponse()
	        throws ClientKeyedInvalidAttributeException,
	            ClientRequestFailureException;

}
