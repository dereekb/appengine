package com.dereekb.gae.client.api.model.crud.builder.impl;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.model.crud.builder.SecuredClientModelRequestSender;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.response.util.ClientApiResponseWrapper;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract {@link SecuredClientModelRequestSender} implementation.
 * 
 * @author dereekb
 *
 * @param <R>
 *            request type
 * @param <S>
 *            serialized response type
 */
public abstract class AbstractSecuredClientModelRequestSender<R, S>
        implements SecuredClientModelRequestSender<R, S> {

	private ObjectMapper objectMapper = new ObjectMapper();

	private SecuredClientApiRequestSender requestSender;
	private ClientRequestSecurity defaultServiceSecurity = new ClientRequestSecurityImpl();

	public AbstractSecuredClientModelRequestSender(SecuredClientApiRequestSender requestSender)
	        throws IllegalArgumentException {
		this.setRequestSender(requestSender);
	}

	public ObjectMapper getObjectMapper() {
		return this.objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		if (objectMapper == null) {
			throw new IllegalArgumentException("ObjectMapper cannot be null.");
		}

		this.objectMapper = objectMapper;
	}

	public SecuredClientApiRequestSender getRequestSender() {
		return this.requestSender;
	}

	public void setRequestSender(SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		if (requestSender == null) {
			throw new IllegalArgumentException("RequestSender cannot be null.");
		}

		this.requestSender = requestSender;
	}

	public ClientRequestSecurity getDefaultServiceSecurity() {
		return this.defaultServiceSecurity;
	}

	public void setDefaultServiceSecurity(ClientRequestSecurity defaultServiceSecurity) {
		if (defaultServiceSecurity == null) {
			throw new IllegalArgumentException("defaultServiceSecurity cannot be null.");
		}

		this.defaultServiceSecurity = defaultServiceSecurity;
	}

	// MARK: SecuredClientModelRequestSender
	@Override
	public SerializedClientApiResponse<S> sendRequest(R request,
	                                                  ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException {

		ClientRequest clientRequest = this.buildClientRequest(request);
		ClientApiResponse clientResponse = this.requestSender.sendRequest(clientRequest, security);
		return new SerializedClientApiResponseImpl(clientResponse);
	}

	/**
	 * Builds a client request from the input data.
	 * 
	 * @param request
	 *            Input request type. Never {@code null}.
	 * @return {@link ClientApiResponse}. Never {@code null}.
	 */
	public abstract ClientRequest buildClientRequest(R request);

	/**
	 * Serializes response data into a new object.
	 * 
	 * @param response
	 *            {@link ClientApiResponse}. Never {@code null}.
	 * @return Serialized response data. Never {@code null}.
	 */
	public abstract S serializeResponseData(ClientApiResponse response) throws ClientResponseSerializationException;

	/**
	 * {@link SerializedClientApiResponse} implementation.
	 * 
	 * @author dereekb
	 *
	 */
	protected class SerializedClientApiResponseImpl extends ClientApiResponseWrapper
	        implements SerializedClientApiResponse<S> {

		private S serializedData;

		public SerializedClientApiResponseImpl(ClientApiResponse response) {
			super(response);
		}

		// MARK: SerializedClientApiResponse
		@Override
		public S getSerializedPrimaryData() throws ClientResponseSerializationException {
			if (this.serializedData == null) {
				this.serializedData = AbstractSecuredClientModelRequestSender.this.serializeResponseData(this.response);
			}

			return this.serializedData;
		}

	}

}
