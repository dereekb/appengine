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
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.options.DeleteRequestOptions;
import com.dereekb.gae.model.extension.data.conversion.TypedBidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.misc.parameters.impl.ParametersImpl;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;

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

	public ClientDeleteRequestSenderImpl(TypedBidirectionalConverter<T, O> typedConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(typedConverter, keyTypeConverter, requestSender);
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
	public ClientDeleteResponse<T> delete(DeleteRequest request,
	                                      ClientRequestSecurity security)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {
		ClientDeleteRequest clientDeleteRequest = new ClientDeleteRequestImpl(request, false);
		return this.delete(clientDeleteRequest, security);
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
		return clientResponse.getSerializedResponse();
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public ClientRequest buildClientRequest(ClientDeleteRequest request) {

		ClientRequestUrl url = this.makeRequestUrl();
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.DELETE);

		ParametersImpl parameters = new ParametersImpl();

		Iterable<ModelKey> modelKeys = request.getTargetKeys();
		String keys = ModelKey.keysAsString(modelKeys, ",");

		DeleteRequestOptions options = request.getOptions();
		parameters.addObjectParameter(EditModelController.ATOMIC_PARAM, options.isAtomic());
		parameters.addObjectParameter(EditModelController.KEYS_PARAM, keys);
		parameters.addObjectParameter(EditModelController.RETURN_MODELS_PARAM, request.shouldReturnModels());

		clientRequest.setParameters(parameters);

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
		private Collection<ModelKey> serializedModelKeys;

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

		@Override
		public Collection<ModelKey> getModelKeys() {
			if (this.request.shouldReturnModels()) {
				Collection<T> models = super.getModels();
				return ModelKey.readModelKeys(models);
			} else {
				if (this.serializedModelKeys == null) {
					ClientApiResponseData data = this.response.getPrimaryData();
					this.serializedModelKeys = ClientDeleteRequestSenderImpl.this.serializeKeys(data);
				}

				return this.serializedModelKeys;
			}
		}

	}

}
