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
import com.dereekb.gae.utilities.collections.pairs.Pair;
import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link ClientRequestFailureException} equivalent of
 * {@link AtomicOperationException}.
 *
 * @author dereekb
 * @see AtomicOperationException
 */
public class ClientAtomicOperationException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	private String type;
	private List<ModelKey> missingKeys;

	public ClientAtomicOperationException(String type, List<ModelKey> missingKeys, ClientApiResponse clientResponse) {
		super(clientResponse);
		this.setType(type);
		this.setMissingKeys(missingKeys);
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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
		 * Calls
		 * {{@link #assertNoAtomicOperationError(String, ClientApiResponse)}
		 * with no type set.
		 *
		 * @param clientResponse
		 *            {@link ClientApiResponse}. Never {@code null}.
		 * @throws ClientAtomicOperationException
		 *             asserted exception.
		 */
		public void assertNoAtomicOperationError(ClientApiResponse clientResponse)
		        throws ClientAtomicOperationException {
			this.assertNoAtomicOperationError(null, clientResponse);
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
				Pair<String, List<ModelKey>> result = this.serializeMissingResourceKeysPair(type, clientResponse);

				List<ModelKey> missingKeys = result.getObject();

				if (missingKeys.isEmpty() == false) {
					throw new ClientAtomicOperationException(result.getKey(), missingKeys, clientResponse);
				}
			}
		}

		public List<ModelKey> serializeMissingResourceKeys(String type,
		                                                   ClientApiResponse response) {
			return this.serializeMissingResourceKeysPair(type, response).getObject();
		}

		/**
		 * Calls
		 * {@link #serializeMissingResourceKeys(String, ClientApiResponse)}
		 * without a type set.
		 *
		 * @param response
		 *            {@link ClientApiResponse}. Never {@code null}.
		 * @return {@link List}. Never {@code null}.
		 */
		public Pair<String, List<ModelKey>> serializeMissingResourceKeysPair(ClientApiResponse response) {
			return this.serializeMissingResourceKeysPair(null, response);
		}

		/**
		 * Serializes keys from the error associated with the atomic operation
		 * exception,
		 * {@link MissingRequiredResourceException#ERROR_CODE}.
		 *
		 * @param type
		 *            Serializess the
		 * @param response
		 *            {@link ClientApiResponse}. Never {@code null}.
		 * @return {@link List}. Never {@code null}.
		 */
		public Pair<String, List<ModelKey>> serializeMissingResourceKeysPair(String type,
		                                                                     ClientApiResponse response) {
			List<ModelKey> objectKeys = null;

			ClientResponseError error = response.getError();
			Map<String, ClientResponseErrorInfo> errorInfoMap = error.getErrorInfoMap();
			ClientResponseErrorInfo missingKeysInfo = errorInfoMap.get(MissingRequiredResourceException.ERROR_CODE);

			// TODO: Read type if available.
			// missingKeysInfo.getErrorData().get

			if (missingKeysInfo != null) {
				JsonNode errorData = missingKeysInfo.getErrorData();

				if (type == null) {
					type = errorData.get(MissingRequiredResourceException.ERROR_DATA_TYPE_CODE).asText();

					if (type == null) {
						throw new IllegalArgumentException("Type was not provided by the response.");
					}
				}

				JsonNode missingKeysData = errorData.get(MissingRequiredResourceException.ERROR_DATA_KEYS_CODE);
				objectKeys = this.keySerializer.serializeKeys(type, missingKeysData);
			} else {
				objectKeys = Collections.emptyList();
			}

			return new HandlerPair<String, List<ModelKey>>(type, objectKeys);
		}

	}

}
