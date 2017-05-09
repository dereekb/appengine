package com.dereekb.gae.client.api.service.response;

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
	 * 
	 * @return Serialized response object. May be {@code null} if allowed by
	 *         implementation.
	 * 
	 * @throws ClientResponseSerializationException
	 *             thrown if there is an issue with serializing the results, or
	 *             if the request failed.
	 */
	public T getSerializedResponse() throws ClientResponseSerializationException;

}
