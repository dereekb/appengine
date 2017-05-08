package com.dereekb.gae.client.api.model.extension.link.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.model.extension.links.components.exception.LinkExceptionReason;
import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangeException.LinkSystemChangeApiResponseError;
import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangeSetException;
import com.dereekb.gae.model.extension.links.service.impl.LinkSystemChangeImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientRequestFailureException} used for wrapping a
 * {@link LinkSystemChangeSetException} client side.
 * 
 * @author dereekb
 *
 */
public class ClientLinkServiceChangeException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	private ClientLinkSystemChangeErrorSet error;

	public ClientLinkServiceChangeException(ClientResponse response, ClientLinkSystemChangeErrorSet error) {
		super(response);
		this.setError(error);
	}

	protected ClientLinkSystemChangeErrorSet getError() {
		return this.error;
	}

	protected void setError(ClientLinkSystemChangeErrorSet error) {
		if (error == null) {
			throw new IllegalArgumentException("error cannot be null.");
		}

		this.error = error;
	}

	/**
	 * Used for serializing a {@link LinkSystemChangeSetException} from errors.
	 * 
	 * @author dereekb
	 *
	 */
	public static class ClientLinkServiceChangeExceptionUtility {

		private ObjectMapper mapper = new ObjectMapper();
		private TypeModelKeyConverter keyConverter;

		public ClientLinkServiceChangeExceptionUtility(TypeModelKeyConverter keySerializer) {
			this.setKeyConverter(keySerializer);
		}

		public ObjectMapper getMapper() {
			return this.mapper;
		}

		public void setMapper(ObjectMapper mapper) {
			if (mapper == null) {
				throw new IllegalArgumentException("mapper cannot be null.");
			}

			this.mapper = mapper;
		}

		public TypeModelKeyConverter getKeyConverter() {
			return this.keyConverter;
		}

		public void setKeyConverter(TypeModelKeyConverter keyConverter) {
			if (keyConverter == null) {
				throw new IllegalArgumentException("keyConverter cannot be null.");
			}

			this.keyConverter = keyConverter;
		}

		// MARK: Utility
		/**
		 * Asserts that the request was successful and there are no link change
		 * errors.
		 * 
		 * @param clientResponse
		 *            {@link ClientApiResponse}. Never {@code null}.
		 * @throws ClientLinkServiceChangeException
		 *             asserted exception.
		 */
		public void assertNoAtomicOperationError(String type,
		                                         ClientApiResponse clientResponse)
		        throws ClientLinkServiceChangeException {
			ClientResponseError error = clientResponse.getError();

			if (error.getErrorType() == ClientApiResponseErrorType.OTHER_BAD_RESPONSE_ERROR) {
				ClientLinkSystemChangeErrorSet errorSet = this.serializeClientLinkSystemChangeErrorSet(type,
				        clientResponse);

				if (errorSet != null) {
					throw new ClientLinkServiceChangeException(clientResponse, errorSet);
				}
			}
		}

		/**
		 * Serializes keys from the error associated with the link change error
		 * set codes,
		 * {@link LinkSystemChangeSetException#API_ERROR_CODE}.
		 * 
		 * @param response
		 *            {@link ClientApiResponse}. Never {@code null}.
		 * @return {@link List}. Never {@code null}.
		 */
		public ClientLinkSystemChangeErrorSet serializeClientLinkSystemChangeErrorSet(String type,
		                                                                              ClientApiResponse response) {
			ClientLinkSystemChangeErrorSet errorSet = null;

			ClientResponseError error = response.getError();
			Map<String, ClientResponseErrorInfo> errorInfoMap = error.getErrorInfoMap();
			ClientResponseErrorInfo errorInfo = errorInfoMap.get(LinkSystemChangeSetException.API_ERROR_CODE);

			if (errorInfo != null) {
				errorSet = this.serializeClientLinkSystemChangeErrorSet(type, errorInfo);
			}

			return errorSet;
		}

		/**
		 * Serializes the error back from {@link
		 * 
		 * @param type
		 * @param errorInfo
		 * @return
		 */
		private ClientLinkSystemChangeErrorSet serializeClientLinkSystemChangeErrorSet(String type,
		                                                                               ClientResponseErrorInfo errorInfo) {
			// Array of error responses.
			JsonNode errorArray = errorInfo.getErrorData();

			LinkSystemChangeApiResponseError[] errors;

			try {
				errors = this.mapper.treeToValue(errorArray, LinkSystemChangeApiResponseError[].class);
			} catch (JsonProcessingException e) {
				throw new ClientResponseSerializationException(e);
			}

			List<LinkSystemChangeApiResponseError> apiErrorsList = ListUtility.toList(errors);
			List<ClientLinkSystemChangeError> errorsList = this.serializerErrors(type, apiErrorsList);
			return new ClientLinkSystemChangeErrorSetImpl(type, errorsList);
		}

		private List<ClientLinkSystemChangeError> serializerErrors(String type,
		                                                           List<LinkSystemChangeApiResponseError> errors) {
			List<ClientLinkSystemChangeError> errorsList = new ArrayList<ClientLinkSystemChangeError>();

			for (LinkSystemChangeApiResponseError error : errors) {
				ClientLinkSystemChangeErrorImpl clientError = this.makeChangeError(type, error);
				errorsList.add(clientError);
			}

			return errorsList;
		}

		public ClientLinkSystemChangeErrorImpl makeChangeError(String type,
		                                                       LinkSystemChangeApiResponseError error) {
			LinkSystemChangeImpl change = new LinkSystemChangeImpl();

			String primaryKeyString = error.getKey();
			ModelKey primaryKey = this.keyConverter.convertKey(type, primaryKeyString);

			change.setAction(LinkChangeAction.fromString(error.getAction()));
			change.setPrimaryType(type);
			change.setPrimaryKey(primaryKey);
			change.setLinkName(error.getLink());
			change.setTargetStringKeys(error.getTargetKeys());

			LinkExceptionReason reason = LinkExceptionReason.fromErrorCode(error.getErrorCode());
			return new ClientLinkSystemChangeErrorImpl(change, reason);
		}

		// MARK: Classes
		private class ClientLinkSystemChangeErrorSetImpl
		        implements ClientLinkSystemChangeErrorSet {

			private String type;
			private List<ClientLinkSystemChangeError> errors;

			public ClientLinkSystemChangeErrorSetImpl(String type, List<ClientLinkSystemChangeError> errors) {
				this.setType(type);
				this.addAllErrors(errors);
			}

			@Override
			public String getType() {
				return this.type;
			}

			protected void setType(String type) {
				if (type == null) {
					throw new IllegalArgumentException("type cannot be null.");
				}

				this.type = type;
			}

			@Override
			public List<ClientLinkSystemChangeError> getErrors() {
				return this.errors;
			}

			private void addAllErrors(List<ClientLinkSystemChangeError> errors) {
				this.errors.addAll(errors);
			}

		}

		// MARK: Internal
		private static class ClientLinkSystemChangeErrorImpl
		        implements ClientLinkSystemChangeError {

			private LinkSystemChange change;
			private LinkExceptionReason reason;

			public ClientLinkSystemChangeErrorImpl(LinkSystemChange change, LinkExceptionReason reason) {
				this.change = change;
				this.reason = reason;
			}

			@Override
			public LinkSystemChange getChange() {
				return this.change;
			}

			@Override
			public LinkExceptionReason getReason() {
				return this.reason;
			}

		}

	}

}
