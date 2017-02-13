package com.dereekb.gae.client.api.service.response.builder;

import com.dereekb.gae.client.api.service.response.ClientApiResponseAccessor;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;

/**
 * Used for building a {@link ClientApiResponseAccessor}.
 * 
 * @author dereekb
 *
 */
public interface ClientApiResponseAccessorBuilder {

	/**
	 * Builds an accessor with a client response.
	 * 
	 * @param response
	 *            {@link ClientResponse}. Never {@code null}.
	 * @return {@link ClientApiResponseAccessor}. Never {@code null}.
	 * @throws NotClientApiResponseException
	 *             thrown if an error occurs marshalling the input.
	 */
	public ClientApiResponseAccessor buildAccessor(ClientResponse response) throws NotClientApiResponseException;

}
