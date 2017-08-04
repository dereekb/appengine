package com.dereekb.gae.client.api.service.response;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;

/**
 * {@link ClientApiResponse} extension that has serialized primary data.
 * 
 * @author dereekb
 *
 * @param <T>
 *            serialized data type
 */
public interface SerializedClientApiResponse<T>
        extends ClientApiResponse {

	/**
	 * Returns the serialized primary response.
	 * <p>
	 * {@link #isSuccessful()} must be true, otherwise an exception will be
	 * raised.
	 * 
	 * @return Serialized response object. May be {@code null} if allowed by
	 *         implementation.
	 * 
	 * @throws ClientRequestFailureException
	 *             thrown if the response indicates an error.
	 * @throws ClientResponseSerializationException
	 *             thrown if there is an issue with serializing the results, or
	 *             if the request failed.
	 */
	public T getSerializedResponse() throws ClientRequestFailureException, ClientResponseSerializationException;

}
