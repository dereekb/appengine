package com.dereekb.gae.client.api.service.sender;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientResponse;

/**
 * Low-level interface for synchronously sending client requests.
 * 
 * @author dereekb
 *
 */
public interface ClientRequestSender {

	/**
	 * Sends a synchronous HTTP request.
	 * 
	 * @param request
	 *            {@link ClientRequest}. Never {@code null}.
	 * @return {@link ClientResponse}. Never {@code null}.
	 * @throws ClientConnectionException
	 *             if a connection error occurs.
	 */
	public ClientResponse sendRequest(ClientRequest request) throws ClientConnectionException;

}
