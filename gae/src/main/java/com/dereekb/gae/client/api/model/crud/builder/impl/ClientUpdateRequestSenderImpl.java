package com.dereekb.gae.client.api.model.crud.builder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.client.api.model.crud.services.ClientUpdateService;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestDataImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.model.crud.services.response.pair.UpdateResponseFailurePair;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.web.api.model.request.ApiUpdateRequest;
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
public class ClientUpdateRequestSenderImpl<T extends UniqueModel, O> extends AbstractClientModelEditRequestSender<T, O, UpdateRequest<T>, UpdateResponse<T>>
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
	public UpdateResponse<T> update(UpdateRequest<T> request)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {

		SerializedClientApiResponse<UpdateResponse<T>> clientResponse = this.sendRequest(request,
		        this.getDefaultServiceSecurity());
		this.assertSuccessfulResponse(clientResponse);
		return clientResponse.getSerializedPrimaryData();
	}

	// MARK: AbstractSecuredClientModelRequestSender
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
	public UpdateResponse<T> serializeResponseData(ClientApiResponse response)
	        throws ClientResponseSerializationException {
		return new ClientUpdateResponseImpl(response);
	}

	private class ClientUpdateResponseImpl
	        implements UpdateResponse<T> {

		private List<T> serializedModels;
		private List<ModelKey> serializedMissingKeys;
		private List<UpdateResponseFailurePair<T>> serializedFailedTemplates;

		private final ClientApiResponse response;

		public ClientUpdateResponseImpl(ClientApiResponse response) {
			this.response = response;
		}

		// MARK: UpdateResponse
		@Override
		public Collection<T> getUpdatedModels() {
			if (this.serializedModels == null) {
				ClientApiResponseData data = this.response.getPrimaryData();
				this.serializedModels = ClientUpdateRequestSenderImpl.this.serializeModels(data);
			}

			return this.serializedModels;
		}

		@Override
		public Collection<ModelKey> getUnavailable() {
			if (this.serializedMissingKeys == null) {
				this.serializedMissingKeys = ClientUpdateRequestSenderImpl.this
				        .serializeMissingResourceKeys(this.response);
			}

			return this.serializedMissingKeys;
		}

		@Override
		public Collection<UpdateResponseFailurePair<T>> getFailed() {
			if (this.serializedFailedTemplates == null) {
				this.serializedFailedTemplates = ClientUpdateRequestSenderImpl.this
				        .serializeFailurePairs(this.response);
			}

			return this.serializedFailedTemplates;
		}

	}

	public List<UpdateResponseFailurePair<T>> serializeFailurePairs(ClientApiResponse response) {

		// TODO: Parse failure pairs.

		return new ArrayList<>();
	}

}
