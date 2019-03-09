package com.dereekb.gae.client.api.service.response.builder;

import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;

/**
 * Used for serializing a {@link ClientResponse} to a {@link ClientApiResponse}.
 * 
 * @author dereekb
 *
 */
public interface ClientApiResponseBuilder {

	/**
	 * Serializes an client API response from the input.
	 * 
	 * @param response
	 *            {@link ClientResponse}. Never {@code null}.
	 * @return {@link ClientApiResponse}. Never {@code null}.
	 * @throws NotClientApiResponseException
	 *             thrown if the response could not be serialized to a response.
	 */
	public ClientApiResponse buildApiResponse(ClientResponse response) throws NotClientApiResponseException;

}
