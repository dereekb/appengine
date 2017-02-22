package com.dereekb.gae.client.api.model.crud.builder.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.model.shared.builder.impl.AbstractConfiguredClientModelRequestSender;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.response.SimpleServiceResponse;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;

/**
 * Abstract client model request sender for all types.
 * 
 * Provides helper functions for asserting responses, etc.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <O>
 *            model dto type
 * @param <R>
 *            request type
 * @param <S>
 *            serialized response type
 */
public abstract class AbstractClientModelCrudRequestSender<T extends UniqueModel, O, R, S> extends AbstractConfiguredClientModelRequestSender<T, O, R, S> {

	public AbstractClientModelCrudRequestSender(String type,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(type, dtoType, dtoConverter, keyTypeConverter, requestSender);
	}

	public AbstractClientModelCrudRequestSender(String type,
	        String pathFormat,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(type, pathFormat, dtoType, dtoConverter, keyTypeConverter, requestSender);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Checks for {@link ClientAtomicOperationException} also.
	 * 
	 */
	@Override
	public void assertSuccessfulResponse(ClientApiResponse clientResponse)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {
		if (clientResponse.getSuccess() == false) {
			this.assertNoAtomicOperationError(clientResponse);
			throw new ClientRequestFailureException(clientResponse);
		}
	}

	/**
	 * Asserts that the request was successful and there is no atomic operation
	 * error.
	 * 
	 * @param clientResponse
	 *            {@link ClientApiResponse}. Never {@code null}.
	 * @throws ClientAtomicOperationException
	 *             asserted exception.
	 */
	public void assertNoAtomicOperationError(ClientApiResponse clientResponse) throws ClientAtomicOperationException {
		ClientResponseError error = clientResponse.getError();

		if (error.getErrorType() == ClientApiResponseErrorType.OTHER_BAD_RESPONSE_ERROR) {
			List<ModelKey> missingKeys = this.serializeMissingResourceKeys(clientResponse);

			if (missingKeys.isEmpty() == false) {
				throw new ClientAtomicOperationException(missingKeys, clientResponse);
			}
		}
	}

	/**
	 * Serializes keys from the error associated with the atomic operation
	 * exception,
	 * {@link MissingRequiredResourceException#API_RESPONSE_ERROR_CODE}.
	 * 
	 * @param response
	 *            {@link ClientApiResponse}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelKey> serializeMissingResourceKeys(ClientApiResponse response) {
		List<ModelKey> objectKeys = null;

		ClientResponseError error = response.getError();
		Map<String, ClientResponseErrorInfo> errorInfoMap = error.getErrorInfoMap();
		ClientResponseErrorInfo missingKeysInfo = errorInfoMap
		        .get(MissingRequiredResourceException.API_RESPONSE_ERROR_CODE);

		if (missingKeysInfo != null) {
			objectKeys = this.serializeKeysFromErrorInfoData(missingKeysInfo);
		} else {
			objectKeys = Collections.emptyList();
		}

		return objectKeys;
	}

	// MARK: Utility Classes
	protected class AbstractClientServiceModelResponseImpl extends AbstractClientServiceResponseImpl {

		private List<T> serializedModels;

		public AbstractClientServiceModelResponseImpl(ClientApiResponse response) {
			super(response);
		}

		// MARK: Models Response
		public Collection<T> getModels() {
			if (this.serializedModels == null) {
				ClientApiResponseData data = this.response.getPrimaryData();
				this.serializedModels = AbstractClientModelCrudRequestSender.this.serializeModels(data);
			}

			return this.serializedModels;
		}

	}

	protected class AbstractClientServiceResponseImpl extends AbstractSerializedResponse
	        implements SimpleServiceResponse {

		private List<ModelKey> serializedMissingKeys;

		public AbstractClientServiceResponseImpl(ClientApiResponse response) {
			super(response);
		}

		// MARK: Get Failed
		@Override
		public Collection<ModelKey> getFailed() {
			if (this.serializedMissingKeys == null) {
				this.serializedMissingKeys = AbstractClientModelCrudRequestSender.this
				        .serializeMissingResourceKeys(this.response);
			}

			return this.serializedMissingKeys;
		}

	}

}
