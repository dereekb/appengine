package com.dereekb.gae.client.api.model.crud.builder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.request.ClientReadRequest;
import com.dereekb.gae.client.api.model.crud.response.SerializedClientReadApiResponse;
import com.dereekb.gae.client.api.model.crud.services.ClientReadService;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.model.exception.LargeAtomicRequestException;
import com.dereekb.gae.client.api.model.shared.request.RelatedTypesRequest;
import com.dereekb.gae.client.api.model.shared.request.impl.RelatedTypesRequestImpl;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.response.exception.NoClientResponseDataException;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.response.SimpleReadResponse;
import com.dereekb.gae.model.crud.services.response.impl.ReadResponseImpl;
import com.dereekb.gae.model.extension.data.conversion.TypedBidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.misc.parameters.impl.ParametersImpl;
import com.dereekb.gae.web.api.model.crud.controller.ReadController;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link ClientReadRequestSender} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <O>
 *            model dto type
 * @see ReadController
 */
public class ClientReadRequestSenderImpl<T extends UniqueModel, O> extends AbstractClientModelCrudRequestSender<T, O, ReadRequest, SimpleReadResponse<T>>
        implements ClientReadRequestSender<T>, ClientReadService<T> {

	/**
	 * GET request to {@code /<type>} by default.
	 */
	public static final String DEFAULT_PATH_FORMAT = "/%s";

	public static final Integer MAX_REQUEST_READ_SIZE = ReadController.MAX_KEYS_PER_REQUEST;

	public static final RelatedTypesRequest DEFAULT_RELATED_REQUEST = new RelatedTypesRequestImpl(false);

	private static Partitioner REQUEST_PARTITIONER = new PartitionerImpl(MAX_REQUEST_READ_SIZE);

	public ClientReadRequestSenderImpl(TypedBidirectionalConverter<T, O> typedConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(typedConverter, keyTypeConverter, requestSender);
	}

	// MARK: Abstract
	@Override
	protected String getDefaultPathFormat() {
		return DEFAULT_PATH_FORMAT;
	}

	// MARK: ClientReadService
	@Override
	public SimpleReadResponse<T> read(ReadRequest request)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {
		return this.read(request, null);
	}

	@Override
	public SimpleReadResponse<T> read(ReadRequest request,
	                                  ClientRequestSecurity security)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {
		SerializedClientReadApiResponse<T> clientResponse = this.sendRequest(request, security);
		return clientResponse.getSerializedResponse();
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public SerializedClientReadApiResponse<T> sendRequest(ReadRequest request,
	                                                      ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {
		ClientRequest clientRequest = this.buildClientRequest(request);
		ClientApiResponse clientResponse = this.sendRequest(clientRequest, security);
		return new SerializedClientReadApiResponseImpl(request, clientResponse, security);
	}

	@Override
	public SerializedClientReadApiResponse<T> sendRequest(ClientReadRequest request,
	                                                      ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		List<List<ModelKey>> partitions = REQUEST_PARTITIONER.makePartitions(request.getModelKeys());

		if (request.getOptions().isAtomic() && partitions.size() > MAX_REQUEST_READ_SIZE) {
			throw new LargeAtomicRequestException();
		}

		if (partitions.size() == 1) {
			ClientRequest clientRequest = this.buildClientRequest(request, request);
			ClientApiResponse clientResponse = this.sendRequest(clientRequest, security);
			return new SerializedClientReadApiResponseImpl(request, clientResponse, security);
		} else {
			List<ClientApiResponse> responses = new ArrayList<ClientApiResponse>();
			ReadRequestOptions options = request.getOptions();

			for (List<ModelKey> partition : partitions) {
				ClientRequest clientRequest = this.buildClientRequest(partition, options, request);
				ClientApiResponse clientResponse = this.sendRequest(clientRequest, security);

				responses.add(clientResponse);

				if (clientResponse.isSuccessful() == false) {
					break;
				}
			}

			return new MultiSerializedClientReadApiResponseImpl(request, responses, security);
		}
	}

	@Override
	public ClientRequest buildClientRequest(ReadRequest request) throws LargeAtomicRequestException {
		return this.buildClientRequest(request, DEFAULT_RELATED_REQUEST);
	}

	public ClientRequest buildClientRequest(ReadRequest request,
	                                        RelatedTypesRequest typesRequest)
	        throws LargeAtomicRequestException {

		List<ModelKey> modelKeys = IteratorUtility.iterableToList(request.getModelKeys());

		// Assert max atomic request size.
		if (request.getOptions().isAtomic() || typesRequest.shouldLoadRelatedTypes()) {
			if (modelKeys.size() > MAX_REQUEST_READ_SIZE) {
				throw new LargeAtomicRequestException();
			}
		}

		ReadRequestOptions options = request.getOptions();
		return this.buildClientRequest(modelKeys, options, typesRequest);
	}

	public ClientRequest buildClientRequest(List<ModelKey> modelKeys,
	                                        ReadRequestOptions options,
	                                        RelatedTypesRequest typesRequest) {

		ClientRequestUrl url = this.makeRequestUrl();
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.GET);

		String keys = ModelKey.keysAsString(modelKeys, ",");

		ParametersImpl parameters = new ParametersImpl();

		parameters.addObjectParameter(ReadController.ATOMIC_PARAM, options.isAtomic());

		if (typesRequest != null) {
			parameters.addObjectParameter(ReadController.LOAD_RELATED_PARAM, typesRequest.shouldLoadRelatedTypes());

			Set<String> filter = typesRequest.getRelatedTypesFilter();

			if (filter != null) {
				String filterString = StringUtility.joinValues(filter);
				parameters.addObjectParameter(ReadController.RELATED_FILTER_PARAM, filterString);
			}
		}

		parameters.addObjectParameter(ReadController.KEYS_PARAM, keys);

		clientRequest.setParameters(parameters);
		return clientRequest;
	}

	@Override
	public SimpleReadResponse<T> serializeResponseData(ReadRequest request,
	                                                   ClientApiResponse response)
	        throws ClientResponseSerializationException {
		return new ClientReadResponseImpl(response);
	}

	private class SerializedClientReadApiResponseImpl extends SerializedClientApiResponseImpl
	        implements SerializedClientReadApiResponse<T> {

		public SerializedClientReadApiResponseImpl(ReadRequest request,
		        ClientApiResponse response,
		        ClientRequestSecurity security) {
			super(request, response, security);
		}

	}

	private class MultiSerializedClientReadApiResponseImpl extends SerializedClientApiResponseImpl
	        implements SerializedClientReadApiResponse<T> {

		private List<ClientApiResponse> responses;

		public MultiSerializedClientReadApiResponseImpl(ReadRequest request,
		        List<ClientApiResponse> responses,
		        ClientRequestSecurity security) {
			super(request, responses.get(0), security);
			this.responses = responses;
		}

		@Override
		public boolean isSuccessful() {
			for (ClientApiResponse response : this.responses) {
				if (response.isSuccessful() == false) {
					return false;
				}
			}

			return true;
		}

		@Override
		public ClientApiResponseData getPrimaryData() throws NoClientResponseDataException {
			throw new UnsupportedOperationException("Primary data unavailable for read requests of this size.");
		}

		@Override
		public Map<String, ClientApiResponseData> getIncludedData() {
			throw new UnsupportedOperationException("Included data unavailable for read requests of this size.");
		}

		/**
		 * Returns the first error that occured.
		 */
		@Override
		public ClientResponseError getError() {
			for (ClientApiResponse response : this.responses) {
				ClientResponseError error = response.getError();

				if (error != null) {
					return error;
				}
			}

			return null;
		}

		@Override
		public int getStatus() {
			for (ClientApiResponse response : this.responses) {
				ClientResponseError error = response.getError();

				if (error != null) {
					return response.getStatus();
				}
			}

			return 200;
		}

		@Override
		public String getResponseData() {
			throw new UnsupportedOperationException("Response data unavailable for read requests of this size.");
		}

		@Override
		public JsonNode getApiResponseJsonNode() {
			throw new UnsupportedOperationException(
			        "Api Response Json Node data unavailable for read requests of this size.");
		}

		// MARK: SerializedClientApiResponseImpl
		@Override
		protected SimpleReadResponse<T> serializeResponse()
		        throws ClientResponseSerializationException,
		            ClientRequestFailureException {

			List<T> models = new ArrayList<T>();
			List<ModelKey> failed = new ArrayList<ModelKey>();

			for (ClientApiResponse response : this.responses) {
				// Assert Success
				assertSuccessfulResponse(response);

				// Read Response
				ClientReadResponseImpl readResponse = new ClientReadResponseImpl(response);

				models.addAll(readResponse.getModels());
				failed.addAll(readResponse.getFailed());
			}

			SimpleReadResponse<T> response = new ReadResponseImpl<T>(models, failed);
			return response;
		}

		// MARK:

	}

	private class ClientReadResponseImpl extends AbstractClientServiceResponseImpl
	        implements SimpleReadResponse<T> {

		private List<T> serializedModels;

		public ClientReadResponseImpl(ClientApiResponse response) {
			super(response);
		}

		// MARK: ReadResponse
		@Override
		public Collection<T> getModels() {
			if (this.serializedModels == null) {
				ClientApiResponseData data = this.response.getPrimaryData();
				this.serializedModels = ClientReadRequestSenderImpl.this.serializeModels(data);
			}

			return this.serializedModels;
		}

	}

}
