package com.dereekb.gae.client.api.model.exception;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.shared.utility.ClientModelKeySerializer;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;

/**
 * {@link ClientRequestFailureException} equivalent of
 * {@link AtomicOperationException}.
 * 
 * @author dereekb
 * @see AtomicOperationException
 */
public class ClientAtomicOperationException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	private List<ModelKey> missingKeys;

	public ClientAtomicOperationException(List<ModelKey> missingKeys, ClientApiResponse clientResponse) {
		super(clientResponse);
		this.missingKeys = missingKeys;
	}

	public ClientAtomicOperationException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientAtomicOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientAtomicOperationException(String message) {
		super(message);
	}

	public ClientAtomicOperationException(Throwable cause) {
		super(cause);
	}

	public List<ModelKey> getMissingKeys() {
		return this.missingKeys;
	}

	public void setMissingKeys(List<ModelKey> missingKeys) {
		this.missingKeys = missingKeys;
	}

	public static class ClientAtomicOperationExceptionUtility {

		private ClientModelKeySerializer keySerializer;

		public ClientAtomicOperationExceptionUtility(ClientModelKeySerializer keySerializer) {
			this.setKeySerializer(keySerializer);
		}

		public ClientModelKeySerializer getKeySerializer() {
			return this.keySerializer;
		}

		public void setKeySerializer(ClientModelKeySerializer keySerializer) {
			if (keySerializer == null) {
				throw new IllegalArgumentException("KeySerializer cannot be null.");
			}

			this.keySerializer = keySerializer;
		}

		/**
		 * Asserts that the request was successful and there is no atomic
		 * operation error.
		 * 
		 * @param clientResponse
		 *            {@link ClientApiResponse}. Never {@code null}.
		 * @throws ClientAtomicOperationException
		 *             asserted exception.
		 */
		public void assertNoAtomicOperationError(String type,
		                                         ClientApiResponse clientResponse)
		        throws ClientAtomicOperationException {
			ClientResponseError error = clientResponse.getError();

			if (error.getErrorType() == ClientApiResponseErrorType.OTHER_BAD_RESPONSE_ERROR) {
				List<ModelKey> missingKeys = this.serializeMissingResourceKeys(type, clientResponse);

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
		public List<ModelKey> serializeMissingResourceKeys(String type,
		                                                   ClientApiResponse response) {
			List<ModelKey> objectKeys = null;

			ClientResponseError error = response.getError();
			Map<String, ClientResponseErrorInfo> errorInfoMap = error.getErrorInfoMap();
			ClientResponseErrorInfo missingKeysInfo = errorInfoMap
			        .get(MissingRequiredResourceException.API_RESPONSE_ERROR_CODE);

			if (missingKeysInfo != null) {
				objectKeys = this.keySerializer.serializeKeysFromErrorInfoData(type, missingKeysInfo);
			} else {
				objectKeys = Collections.emptyList();
			}

			return objectKeys;
		}

	}

}
