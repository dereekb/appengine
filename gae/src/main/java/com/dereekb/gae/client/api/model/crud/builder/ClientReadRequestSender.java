package com.dereekb.gae.client.api.model.crud.builder;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.request.ClientReadRequest;
import com.dereekb.gae.client.api.model.crud.response.SerializedClientReadApiResponse;
import com.dereekb.gae.client.api.model.crud.services.ClientReadService;
import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.SimpleReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ClientReadService} and {@link SecuredClientModelRequestSender}
 * extension interface.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ClientReadRequestSender<T extends UniqueModel>
        extends ClientReadService<T>, SecuredClientModelRequestSender<ReadRequest, SimpleReadResponse<T>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SerializedClientReadApiResponse<T> sendRequest(ReadRequest request,
	                                                      ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException;

	/**
	 * Sends a request and returns a serialized response. If the
	 * {@link ClientReadRequest} requests related types be returned, the data
	 * will be accessible via
	 * {@link SerializedClientApiResponse#getIncludedData()}.
	 * 
	 * @param request
	 *            {@link ClientReadRequest}. Never {@code null}.
	 * @param security
	 *            {@link ClientRequestSecurity}. Never {@code null}.
	 * @return {@link SerializedClientApiRespons}. Never {@code null}.
	 * @throws NotClientApiResponseException
	 *             thrown if the response cannot be serialized.
	 * @throws ClientConnectionException
	 *             thrown if the request fails due to connection reasons.
	 * @throws ClientAuthenticationException
	 *             thrown if the request fails due to authentication reasons.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails due to any other reason.
	 */
	public SerializedClientReadApiResponse<T> sendRequest(ClientReadRequest request,
	                                                      ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException;

}
