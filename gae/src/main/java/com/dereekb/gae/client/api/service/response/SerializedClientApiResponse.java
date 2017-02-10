package com.dereekb.gae.client.api.service.response;

/**
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
