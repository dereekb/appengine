package com.dereekb.gae.client.api.model.crud.builder.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.exception.ClientTooMuchInputException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException.ClientAtomicOperationExceptionUtility;
import com.dereekb.gae.client.api.model.shared.builder.impl.AbstractConfiguredClientModelRequestSender;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.response.SimpleServiceResponse;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;

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

	private ClientAtomicOperationExceptionUtility atomicOperationUtility;

	public AbstractClientModelCrudRequestSender(String type,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		this(type, null, dtoType, dtoConverter, keyTypeConverter, requestSender);
	}

	public AbstractClientModelCrudRequestSender(String type,
	        String pathFormat,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(type, pathFormat, dtoType, dtoConverter, keyTypeConverter, requestSender);
		this.atomicOperationUtility = new ClientAtomicOperationExceptionUtility(this.getKeySerializer());
	}

	// MARK: Utility
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
			this.assertNotTooMuchInputError(clientResponse);
			throw new ClientRequestFailureException(clientResponse);
		}
	}

	public void assertNoAtomicOperationError(ClientApiResponse clientResponse) throws ClientAtomicOperationException {
		this.atomicOperationUtility.assertNoAtomicOperationError(this.getType(), clientResponse);
	}

	public void assertNotTooMuchInputError(ClientApiResponse clientResponse) throws ClientTooMuchInputException {
		ClientTooMuchInputException.assertNotTooMuchInputException(clientResponse);
	}

	public List<ModelKey> serializeMissingResourceKeys(ClientApiResponse response) {
		return this.atomicOperationUtility.serializeMissingResourceKeys(this.getType(), response);
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
