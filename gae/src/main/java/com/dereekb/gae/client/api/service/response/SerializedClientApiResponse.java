package com.dereekb.gae.client.api.service.response;

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

	public T getSerializedPrimaryData();

}
