package com.dereekb.gae.client.api.service.sender;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;

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
	 * @throws NotClientApiResponseException
	 *             thrown if the response is not a {@link ClientApiResponse}
	 */
	@Override
	public ClientApiResponse sendRequest(ClientRequest request)
	        throws NotClientApiResponseException,
	            ClientConnectionException;

}
