package com.dereekb.gae.client.api.model.shared.builder.impl;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.response.util.ClientApiResponseWrapper;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
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

	private ObjectMapper objectMapper = ObjectMapperUtilityBuilderImpl.MAPPER;

	private SecuredClientApiRequestSender requestSender;
	private ClientRequestSecurity defaultServiceSecurity = ClientRequestSecurityImpl.none();

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
			throw new IllegalArgumentException("DefaultServiceSecurity cannot be null.");
		}

		this.defaultServiceSecurity = defaultServiceSecurity;
	}

	// MARK: SecuredClientModelRequestSender
	@Override
	public SerializedClientApiResponse<S> sendRequest(R request,
	                                                  ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		ClientRequest clientRequest = this.buildClientRequest(request);
		return this.sendRequest(request, clientRequest, security);
	}

	protected SerializedClientApiResponse<S> sendRequest(R request,
	                                                     ClientRequest clientRequest,
	                                                     ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		ClientApiResponse clientResponse = this.sendRequest(clientRequest, security);
		return new SerializedClientApiResponseImpl(request, clientResponse);
	}

	protected ClientApiResponse sendRequest(ClientRequest clientRequest,
	                                        ClientRequestSecurity security)
	        throws NoSecurityContextException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {
		if (security == null) {
			security = this.getDefaultServiceSecurity();
		}

		ClientApiResponse clientResponse = this.requestSender.sendRequest(clientRequest, security);
		return clientResponse;
	}

	// MARK: Abstract
	/**
	 * Builds a client request from the input data.
	 *
	 * @param request
	 *            Input request type. Never {@code null}.
	 * @return {@link ClientApiResponse}. Never {@code null}.
	 * @throws ClientRequestFailureException thrown if the request is invalid.
	 */
	public abstract ClientRequest buildClientRequest(R request) throws ClientRequestFailureException;

	/**
	 * Serializes response data into a new object.
	 *
	 * @param request
	 *            Request. Never {@code null}.
	 * @param response
	 *            {@link ClientApiResponse}. Never {@code null}.
	 * @param security
	 *            {@link ClientRequestSecurity}. Never {@code null}.
	 * @return Serialized response data. Never {@code null}.
	 * @throws ClientResponseSerializationException
	 *             if the serialization fails.
	 */
	public S serializeResponseData(R request,
	                               ClientApiResponse response,
	                               ClientRequestSecurity security)
	        throws ClientResponseSerializationException {

		// By default does not include security.
		return this.serializeResponseData(request, response);
	}

	/**
	 * Serializes response data into a new object.
	 *
	 * @param request
	 *            Request. Never {@code null}.
	 * @param response
	 *            {@link ClientApiResponse}. Never {@code null}.
	 * @return Serialized response data. Never {@code null}.
	 * @throws ClientResponseSerializationException
	 *             if the serialization fails.
	 */
	public abstract S serializeResponseData(R request,
	                                        ClientApiResponse response)
	        throws ClientResponseSerializationException;

	/**
	 * {@link SerializedClientApiResponse} implementation.
	 *
	 * @author dereekb
	 *
	 */
	protected class SerializedClientApiResponseImpl extends ClientApiResponseWrapper
	        implements SerializedClientApiResponse<S> {

		private R request;
		private S serializedData;
		private ClientRequestSecurity security;

		public SerializedClientApiResponseImpl(R request, ClientApiResponse response) {
			this(request, response, null);
		}

		public SerializedClientApiResponseImpl(R request, ClientApiResponse response, ClientRequestSecurity security) {
			super(response);
			this.setRequest(request);
			this.setSecurity(security);
		}

		public R getRequest() {
			return this.request;
		}

		public void setRequest(R request) {
			this.request = request;
		}

		public ClientRequestSecurity getSecurity() {
			return this.security;
		}

		public void setSecurity(ClientRequestSecurity security) {
			this.security = security;
		}

		// MARK: SerializedClientApiResponse
		@Override
		public S getSerializedResponse() throws ClientRequestFailureException, ClientResponseSerializationException {
			if (this.serializedData == null) {
				this.serializedData = this.serializeResponse();
			}

			return this.serializedData;
		}

		protected S serializeResponse() throws ClientResponseSerializationException, ClientRequestFailureException {
			this.assertResponseSuccess();
			return AbstractSecuredClientModelRequestSender.this.serializeResponseData(this.request, this.response,
			        this.security);
		}

		protected void assertResponseSuccess()
		        throws ClientResponseSerializationException,
		            ClientRequestFailureException {
			AbstractSecuredClientModelRequestSender.this.assertSuccessfulResponse(this);
		}

	}

	/**
	 * Asserts that the request was successful.
	 *
	 * @param clientResponse
	 *            {@link ClientApiResponse}. Never {@code null}.
	 * @throws ClientRequestFailureException
	 *             asserted exception.
	 */
	protected void assertSuccessfulResponse(ClientApiResponse response)
	        throws ClientResponseSerializationException,
	            ClientRequestFailureException {
		if (response.isSuccessful() == false) {
			throw new ClientRequestFailureException(response);
		}
	}

	protected class AbstractSerializedResponse {

		protected final ClientApiResponse response;
		protected ClientRequestSecurity security;

		public AbstractSerializedResponse(ClientApiResponse response) {
			this(response, null);
		}

		public AbstractSerializedResponse(ClientApiResponse response, ClientRequestSecurity security) {
			this.response = response;
			this.setSecurity(security);
		}

		public ClientRequestSecurity getSecurity() {
			return this.security;
		}

		public void setSecurity(ClientRequestSecurity security) {
			this.security = security;
		}

	}
}
