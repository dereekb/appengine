package com.dereekb.gae.client.api.service.sender;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;

/**
 * {@link ClientRequestSender} that returns {@link ClientApiResponse} objects.
 * 
 * @author dereekb
 *
 */
public interface ClientApiRequestSender
        extends ClientRequestSender {

	/**
	 * {@inheritDoc}
	 * 
	 * @return {@link ClientApiResponse}. Never {@code null}.
	 */
	@Override
	public ClientApiResponse sendRequest(ClientRequest request);

}
