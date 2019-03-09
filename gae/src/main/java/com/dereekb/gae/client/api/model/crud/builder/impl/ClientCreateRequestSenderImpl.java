package com.dereekb.gae.client.api.model.crud.builder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientCreateRequestSender;
import com.dereekb.gae.client.api.model.crud.response.SerializedClientCreateApiResponse;
import com.dereekb.gae.client.api.model.crud.services.ClientCreateService;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.model.exception.ClientKeyedInvalidAttributeException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestDataImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.options.CreateRequestOptions;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.pair.InvalidCreateTemplatePair;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.web.api.model.crud.request.ApiCreateRequest;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientCreateRequestSender} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <O>
 *            model dto type
 */
public class ClientCreateRequestSenderImpl<T extends UniqueModel, O> extends AbstractClientModelTemplateRequestSender<T, O, CreateRequest<T>, CreateResponse<T>>
        implements ClientCreateRequestSender<T>, ClientCreateService<T> {

	/**
	 * POST request to {@code /<type>/create} by default.
	 */
	public static final String DEFAULT_PATH_FORMAT = "/%s/create";

	public ClientCreateRequestSenderImpl(String type,
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

	// MARK: Client Create Request Sender
	@Override
	public CreateResponse<T> create(CreateRequest<T> request)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {
		return this.create(request, null);
	}

	@Override
	public CreateResponse<T> create(CreateRequest<T> request,
	                                ClientRequestSecurity security)
	        throws ClientAtomicOperationException,
	            ClientKeyedInvalidAttributeException,
	            ClientRequestFailureException {
		SerializedClientCreateApiResponse<T> clientResponse = this.sendRequest(request, security);
		return clientResponse.getSerializedResponse();
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public SerializedClientCreateApiResponse<T> sendRequest(CreateRequest<T> request,
	                                                        ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {
		ClientRequest clientRequest = this.buildClientRequest(request);
		ClientApiResponse clientResponse = this.sendRequest(clientRequest, security);
		return new SerializedClientCreateApiResponseImpl(request, clientResponse, security);
	}

	@Override
	public ClientRequest buildClientRequest(CreateRequest<T> request) {

		ClientRequestUrl url = this.makeRequestUrl();
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.POST);

		ObjectMapper mapper = this.getObjectMapper();

		ApiCreateRequest<O> createRequest = new ApiCreateRequest<O>();

		CreateRequestOptions options = request.getOptions();
		createRequest.setOptions(options);

		Collection<T> templates = request.getTemplates();
		BidirectionalConverter<T, O> converter = this.getDtoConverter();

		List<O> templateDtos = converter.convertTo(templates);
		createRequest.setData(templateDtos);

		ClientRequestDataImpl requestData = ClientRequestDataImpl.make(mapper, createRequest);
		clientRequest.setData(requestData);

		return clientRequest;
	}

	@Override
	public CreateResponse<T> serializeResponseData(CreateRequest<T> request,
	                                               ClientApiResponse response)
	        throws ClientResponseSerializationException {
		return new ClientCreateResponseImpl(request, response);
	}

	protected class SerializedClientCreateApiResponseImpl extends SerializedClientApiResponseImpl
	        implements SerializedClientCreateApiResponse<T> {

		public SerializedClientCreateApiResponseImpl(CreateRequest<T> request,
		        ClientApiResponse response,
		        ClientRequestSecurity security) {
			super(request, response, security);
		}

	}

	protected class ClientCreateResponseImpl extends AbstractClientServiceModelResponseImpl
	        implements CreateResponse<T> {

		private CreateRequest<T> request;

		private List<? extends InvalidCreateTemplatePair<T>> serializedFailedTemplates;

		public ClientCreateResponseImpl(CreateRequest<T> request, ClientApiResponse response) {
			super(response);
			this.request = request;
		}

		// MARK: CreateResponse
		@Override
		public Collection<? extends InvalidCreateTemplatePair<T>> getInvalidTemplates() {
			if (this.serializedFailedTemplates == null) {
				this.serializedFailedTemplates = this.buildInvalidTemplatePairs();
			}

			return this.serializedFailedTemplates;
		}

		private List<? extends InvalidCreateTemplatePair<T>> buildInvalidTemplatePairs() {
			List<T> templates = this.request.getTemplates();
			List<KeyedInvalidAttribute> attributes = ClientCreateRequestSenderImpl.this
			        .serializeInvalidAttributes(this.response);

			List<InvalidCreateTemplatePair<T>> pairs = new ArrayList<InvalidCreateTemplatePair<T>>();

			for (KeyedInvalidAttribute attribute : attributes) {
				UniqueModel keyHolder = attribute.keyValue();
				ModelKey key = keyHolder.getModelKey();

				int index = key.getId().intValue();
				T template = templates.get(index);

				InvalidCreateTemplatePair<T> pair = new InvalidCreateTemplatePair<T>(index, template, attribute);
				pairs.add(pair);
			}

			return pairs;
		}

	}

}
