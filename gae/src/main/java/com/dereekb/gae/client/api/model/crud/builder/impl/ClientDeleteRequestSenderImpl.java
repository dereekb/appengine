package com.dereekb.gae.client.api.model.crud.builder.impl;

import java.util.Collection;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.request.ClientDeleteRequest;
import com.dereekb.gae.client.api.model.crud.request.impl.ClientDeleteRequestImpl;
import com.dereekb.gae.client.api.model.crud.response.ClientDeleteResponse;
import com.dereekb.gae.client.api.model.crud.services.ClientDeleteService;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestDataImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.options.DeleteRequestOptions;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.web.api.model.request.ApiDeleteRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientDeleteRequestSender} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <O>
 *            model dto type
 */
public class ClientDeleteRequestSenderImpl<T extends UniqueModel, O> extends AbstractClientModelCrudRequestSender<T, O, ClientDeleteRequest, ClientDeleteResponse<T>>
        implements ClientDeleteRequestSender<T>, ClientDeleteService<T> {

	/**
	 * PUT request to {@code /<type>/delete} by default.
	 */
	public static final String DEFAULT_PATH_FORMAT = "/%s/delete";

	public ClientDeleteRequestSenderImpl(String type,
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

	// MARK: Client Delete Request Sender
	@Override
	public ClientDeleteResponse<T> delete(DeleteRequest request)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {
		ClientDeleteRequest clientDeleteRequest = new ClientDeleteRequestImpl(request, false);
		return this.delete(clientDeleteRequest);
	}

	@Override
	public ClientDeleteResponse<T> delete(ClientDeleteRequest request)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {
		return this.delete(request, null);
	}

	@Override
	public ClientDeleteResponse<T> delete(ClientDeleteRequest request,
	                                      ClientRequestSecurity security)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {
		SerializedClientApiResponse<ClientDeleteResponse<T>> clientResponse = this.sendRequest(request, security);
		this.assertSuccessfulResponse(clientResponse);
		return clientResponse.getSerializedPrimaryData();
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public ClientRequest buildClientRequest(ClientDeleteRequest request) {

		ClientRequestUrl url = this.makeRequestUrl();
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.DELETE);

		ObjectMapper mapper = this.getObjectMapper();

		ApiDeleteRequest apiDeleteRequest = new ApiDeleteRequest();
		apiDeleteRequest.setReturnModels(request.shouldReturnModels());

		DeleteRequestOptions options = request.getOptions();
		apiDeleteRequest.setOptions(options);

		Collection<ModelKey> targetKeys = request.getTargetKeys();
		apiDeleteRequest.setTargetKeys(targetKeys);

		ClientRequestDataImpl requestData = ClientRequestDataImpl.make(mapper, apiDeleteRequest);
		clientRequest.setData(requestData);

		return clientRequest;
	}

	@Override
	public ClientDeleteResponse<T> serializeResponseData(ClientDeleteRequest request,
	                                                     ClientApiResponse response)
	        throws ClientResponseSerializationException {
		return new ClientDeleteResponseImpl(request, response);
	}

	private class ClientDeleteResponseImpl extends AbstractClientServiceModelResponseImpl
	        implements ClientDeleteResponse<T> {

		private final ClientDeleteRequest request;

		public ClientDeleteResponseImpl(ClientDeleteRequest request, ClientApiResponse response) {
			super(response);
			this.request = request;
		}

		@Override
		public Collection<T> getModels() throws UnsupportedOperationException {
			if (this.request.shouldReturnModels()) {
				return super.getModels();
			} else {
				throw new UnsupportedOperationException("Request not configured to return models.");
			}
		}

	}

}
