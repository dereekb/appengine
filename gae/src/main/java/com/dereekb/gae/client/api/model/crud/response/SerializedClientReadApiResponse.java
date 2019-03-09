package com.dereekb.gae.client.api.model.crud.response;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.model.crud.services.response.SimpleReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link SerializedClientApiResponse} extension for read requests.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface SerializedClientReadApiResponse<T extends UniqueModel>
        extends SerializedClientApiResponse<SimpleReadResponse<T>> {

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ClientAtomicOperationException
	 *             if the request was atomic and not all requested models could
	 *             not be returned.
	 */
	@Override
	public SimpleReadResponse<T> getSerializedResponse()
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException,
	            ClientResponseSerializationException;

}
