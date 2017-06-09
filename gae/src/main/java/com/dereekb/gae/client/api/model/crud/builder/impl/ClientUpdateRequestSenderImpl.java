package com.dereekb.gae.client.api.model.crud.builder.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.client.api.model.crud.response.SerializedClientUpdateApiResponse;
import com.dereekb.gae.client.api.model.crud.services.ClientUpdateService;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestDataImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.dereekb.gae.model.crud.services.response.SimpleUpdateResponse;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.web.api.model.crud.request.ApiUpdateRequest;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientUpdateRequestSender} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <O>
 *            model dto type
 */
public class ClientUpdateRequestSenderImpl<T extends UniqueModel, O> extends AbstractClientModelTemplateRequestSender<T, O, UpdateRequest<T>, SimpleUpdateResponse<T>>
        implements ClientUpdateRequestSender<T>, ClientUpdateService<T> {

	/**
	 * PUT request to {@code /<type>/update} by default.
	 */
	public static final String DEFAULT_PATH_FORMAT = "/%s/update";

	public ClientUpdateRequestSenderImpl(String type,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoReader,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(type, dtoType, dtoReader, keyTypeConverter, requestSender);
	}

	// MARK: Abstract
	@Override
	protected String getDefaultPathFormat() {
		return DEFAULT_PATH_FORMAT;
	}

	// MARK: Client Update Request Sender
	@Override
	public SimpleUpdateResponse<T> update(UpdateRequest<T> request)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {
		return this.update(request, null);
	}

	@Override
	public SimpleUpdateResponse<T> update(UpdateRequest<T> request,
	                                      ClientRequestSecurity security)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {

		SerializedClientApiResponse<SimpleUpdateResponse<T>> clientResponse = this.sendRequest(request, security);
		return clientResponse.getSerializedResponse();
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public SerializedClientUpdateApiResponse<T> sendRequest(UpdateRequest<T> request,
	                                                        ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {
		ClientRequest clientRequest = this.buildClientRequest(request);
		ClientApiResponse clientResponse = this.sendRequest(clientRequest, security);
		return new SerializedClientUpdateApiResponseImpl(request, clientResponse, security);
	}

	@Override
	public ClientRequest buildClientRequest(UpdateRequest<T> request) {

		ClientRequestUrl url = this.makeRequestUrl();
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.PUT);

		ObjectMapper mapper = this.getObjectMapper();

		ApiUpdateRequest<O> updateRequest = new ApiUpdateRequest<O>();

		UpdateRequestOptions options = request.getOptions();
		updateRequest.setOptions(options);

		Collection<T> templates = request.getTemplates();
		BidirectionalConverter<T, O> converter = this.getDtoConverter();

		List<O> templateDtos = converter.convertTo(templates);
		updateRequest.setData(templateDtos);

		ClientRequestDataImpl requestData = ClientRequestDataImpl.make(mapper, updateRequest);
		clientRequest.setData(requestData);

		return clientRequest;
	}

	@Override
	public SimpleUpdateResponse<T> serializeResponseData(UpdateRequest<T> request,
	                                                     ClientApiResponse response)
	        throws ClientResponseSerializationException {
		return new ClientUpdateResponseImpl(response);
	}

	protected class SerializedClientUpdateApiResponseImpl extends SerializedClientApiResponseImpl
	        implements SerializedClientUpdateApiResponse<T> {

		public SerializedClientUpdateApiResponseImpl(UpdateRequest<T> request,
		        ClientApiResponse response,
		        ClientRequestSecurity security) {
			super(request, response, security);
		}

	}

	protected class ClientUpdateResponseImpl extends AbstractClientServiceModelResponseImpl
	        implements SimpleUpdateResponse<T> {

		private List<KeyedInvalidAttribute> serializedFailures;

		public ClientUpdateResponseImpl(ClientApiResponse response) {
			super(response);
		}

		// MARK: UpdateResponse
		@Override
		public Collection<ModelKey> getFailed() {
			Collection<KeyedInvalidAttribute> failurePairs = this.getUpdateFailures();

			List<ModelKey> keys = ModelKey.readModelKeysFromKeyed(failurePairs);
			keys.addAll(this.getMissingKeys());

			return keys;
		}

		@Override
		public Collection<ModelKey> getMissingKeys() {
			return super.getFailed();
		}

		@Override
		public Collection<KeyedInvalidAttribute> getUpdateFailures() {
			if (this.serializedFailures == null) {
				this.serializedFailures = ClientUpdateRequestSenderImpl.this.serializeInvalidAttributes(this.response);
			}

			return this.serializedFailures;
		}

	}

}
