package com.dereekb.gae.client.api.model.extension.link.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.model.extension.links.service.exception.LinkServiceChangeException.LinkSystemChangeApiResponseError;
import com.dereekb.gae.model.extension.links.service.exception.LinkServiceChangeSetException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemRequestImpl;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientRequestFailureException} used for wrapping a
 * {@link LinkServiceChangeSetException} client side.
 *
 * @author dereekb
 *
 */
public class ClientLinkServiceChangeException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	private ClientLinkSystemChangeErrorSet errorSet;

	public ClientLinkServiceChangeException(ClientResponse response, ClientLinkSystemChangeErrorSet error) {
		super(response);
		this.setErrorSet(error);
	}

	public ClientLinkSystemChangeErrorSet getErrorSet() {
		return this.errorSet;
	}

	public void setErrorSet(ClientLinkSystemChangeErrorSet errorSet) {
		if (errorSet == null) {
			throw new IllegalArgumentException("errorSet cannot be null.");
		}

		this.errorSet = errorSet;
	}

	/**
	 * Used for serializing a {@link LinkServiceChangeSetException} from errors.
	 *
	 * @author dereekb
	 *
	 */
	public static class ClientLinkServiceChangeExceptionUtility {

		private ObjectMapper mapper = ObjectMapperUtilityBuilderImpl.MAPPER;
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
		public void assertNoClientLinkSystemChangeError(String type,
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
		 * {@link LinkServiceChangeSetException#API_ERROR_CODE}.
		 *
		 * @param type {@link String} primary model type.
		 * @param response
		 *            {@link ClientApiResponse}. Never {@code null}.
		 * @return {@link ClientLinkSystemChangeErrorSet}. Never {@code null}.
		 */
		public ClientLinkSystemChangeErrorSet serializeClientLinkSystemChangeErrorSet(String type,
		                                                                              ClientApiResponse response) {
			ClientLinkSystemChangeErrorSet errorSet = null;

			ClientResponseError error = response.getError();
			Map<String, ClientResponseErrorInfo> errorInfoMap = error.getErrorInfoMap();
			ClientResponseErrorInfo errorInfo = errorInfoMap.get(LinkServiceChangeSetException.API_ERROR_CODE);

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
			LinkModificationSystemRequestImpl change = new LinkModificationSystemRequestImpl();

			String primaryKeyString = error.getKey();

			change.setLinkChangeType(MutableLinkChangeType.fromString(error.getAction()));
			change.setLinkModelType(type);
			change.setPrimaryKey(primaryKeyString);
			change.setLinkName(error.getLink());
			change.setKeys(ValueUtility.defaultTo(error.getTargetKeys(), Collections.emptyList()));

			LinkExceptionReason reason = LinkExceptionReason.fromErrorCode(error.getErrorCode());
			return new ClientLinkSystemChangeErrorImpl(change, reason);
		}

		// MARK: Classes
		private class ClientLinkSystemChangeErrorSetImpl
		        implements ClientLinkSystemChangeErrorSet {

			private String type;
			private List<ClientLinkSystemChangeError> errors = new ArrayList<>();

			public ClientLinkSystemChangeErrorSetImpl(String type, List<ClientLinkSystemChangeError> errors) {
				this.setType(type);
				this.setErrors(errors);
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

			public void setErrors(List<ClientLinkSystemChangeError> errors) {
				if (errors == null) {
					throw new IllegalArgumentException("errors cannot be null.");
				}

				this.errors = errors;
			}

		}

		// MARK: Internal
		private static class ClientLinkSystemChangeErrorImpl
		        implements ClientLinkSystemChangeError {

			private LinkModificationSystemRequest change;
			private LinkExceptionReason reason;

			public ClientLinkSystemChangeErrorImpl(LinkModificationSystemRequest change, LinkExceptionReason reason) {
				this.change = change;
				this.reason = reason;
			}

			@Override
			public LinkModificationSystemRequest getChange() {
				return this.change;
			}

			@Override
			public LinkExceptionReason getReason() {
				return this.reason;
			}

		}

	}

}
