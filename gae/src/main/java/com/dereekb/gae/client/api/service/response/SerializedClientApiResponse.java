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

	public T getSerializedPrimaryData() throws ClientResponseSerializationException;

}
