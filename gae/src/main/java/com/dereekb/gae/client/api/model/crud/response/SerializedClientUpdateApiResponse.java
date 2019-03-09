package com.dereekb.gae.client.api.model.crud.response;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.model.exception.ClientKeyedInvalidAttributeException;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.model.crud.services.response.SimpleUpdateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link SerializedClientApiResponse} extension for read requests.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface SerializedClientUpdateApiResponse<T extends UniqueModel>
        extends SerializedClientApiResponse<SimpleUpdateResponse<T>> {

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientKeyedInvalidAttributeException
	 *             if one or more templates are invalid.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	@Override
	public SimpleUpdateResponse<T> getSerializedResponse()
	        throws ClientAtomicOperationException,
	            ClientKeyedInvalidAttributeException,
	            ClientRequestFailureException,
	            ClientResponseSerializationException;

}
