package com.dereekb.gae.client.api.model.crud.builder;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;

/**
 * Abstract interface for client models.
 * 
 * @author dereekb
 *
 * @param <R>
 *            request type
 * @param <S>
 *            serialized response type
 */
public interface SecuredClientModelRequestSender<R, S> {

	/**
	 * Sends a request and returns a serialized response.
	 * 
	 * @param request
	 *            Request. Never {@code null}.
	 * @param security
	 *            {@link ClientRequestSecurity}. Never {@code null}.
	 * @return {@link SerializedClientApiRespons}. Never {@code null}.
	 * @throws NotClientApiResponseException
	 *             thrown if the response cannot be serialized.
	 * @throws ClientConnectionException
	 *             thrown if the client connection fails.
	 */
	public SerializedClientApiResponse<S> sendRequest(R request,
	                                                  ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException;

}
