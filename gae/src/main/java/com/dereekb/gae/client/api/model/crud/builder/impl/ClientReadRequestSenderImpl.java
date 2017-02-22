package com.dereekb.gae.client.api.model.crud.builder.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.request.ClientReadRequest;
import com.dereekb.gae.client.api.model.crud.services.ClientReadService;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.response.SimpleReadResponse;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.misc.parameters.impl.ParametersImpl;
import com.dereekb.gae.web.api.model.crud.controller.ReadController;

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

	public static final boolean DEFAULT_LOAD_RELATED = false;

	public ClientReadRequestSenderImpl(String type,
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

		SerializedClientApiResponse<SimpleReadResponse<T>> clientResponse = this.sendRequest(request, security);
		this.assertSuccessfulResponse(clientResponse);
		return clientResponse.getSerializedPrimaryData();
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public SerializedClientApiResponse<SimpleReadResponse<T>> sendRequest(ClientReadRequest request,
	                                                                      ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {
		ClientRequest clientRequest = this.buildClientRequest(request.shouldLoadRelatedTypes(), request);
		return this.sendRequest(request, clientRequest, security);
	}

	@Override
	public ClientRequest buildClientRequest(ReadRequest request) {
		return this.buildClientRequest(DEFAULT_LOAD_RELATED, request);
	}

	public ClientRequest buildClientRequest(boolean loadRelatedTypes,
	                                        ReadRequest request) {

		ClientRequestUrl url = this.makeRequestUrl();
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.GET);

		ReadRequestOptions options = request.getOptions();
		Iterable<ModelKey> modelKeys = request.getModelKeys();
		String keys = ModelKey.keysAsString(modelKeys, ",");

		ParametersImpl parameters = new ParametersImpl();

		parameters.addObjectParameter(ReadController.ATOMIC_PARAM, options.isAtomic());
		parameters.addObjectParameter(ReadController.LOAD_RELATED_PARAM, loadRelatedTypes);
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
