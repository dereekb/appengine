package com.dereekb.gae.client.api.model.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.builder.KeyedInvalidAttributeApiResponseBuilder;
import com.dereekb.gae.web.api.util.attribute.impl.KeyedInvalidAttributeData;
import com.dereekb.gae.web.api.util.attribute.impl.KeyedInvalidAttributeImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientRequestFailureException} that lists all
 * {@link KeyedInvalidAttribute} values.
 *
 * @author dereekb
 *
 */
public class ClientKeyedInvalidAttributeException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	private List<KeyedInvalidAttribute> invalidAttributes;

	public ClientKeyedInvalidAttributeException(List<KeyedInvalidAttribute> invalidAttributes,
	        ClientApiResponse clientResponse) {
		super(clientResponse);
		this.invalidAttributes = invalidAttributes;
	}

	public ClientKeyedInvalidAttributeException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientKeyedInvalidAttributeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientKeyedInvalidAttributeException(String message) {
		super(message);
	}

	public ClientKeyedInvalidAttributeException(Throwable cause) {
		super(cause);
	}

	public List<KeyedInvalidAttribute> getInvalidAttributes() {
		return this.invalidAttributes;
	}

	public void setInvalidAttributes(List<KeyedInvalidAttribute> invalidAttributes) {
		this.invalidAttributes = invalidAttributes;
	}

	// MARK: Utility
	public static ClientInvalidAttributeExceptionUtility utility(ObjectMapper mapper) {
		return new ClientInvalidAttributeExceptionUtility(mapper);
	}

	public static ClientKeyedInvalidAttributeExceptionUtility utility(TypeModelKeyConverter typeKeyConverter,
	                                                                  ObjectMapper mapper,
	                                                                  String type) {
		return new ClientKeyedInvalidAttributeExceptionUtility(typeKeyConverter, mapper, type);
	}

	public static class ClientInvalidAttributeExceptionUtility {

		private ObjectMapper mapper;

		public ClientInvalidAttributeExceptionUtility(ObjectMapper mapper) {
			super();
			this.setMapper(mapper);
		}

		public ObjectMapper getMapper() {
			return this.mapper;
		}

		public void setMapper(ObjectMapper mapper) {
			if (mapper == null) {
				throw new IllegalArgumentException("Mapper cannot be null.");
			}

			this.mapper = mapper;
		}

		// MARK: Assert
		public void assertNoInvalidAttributes(ClientApiResponse clientResponse)
		        throws ClientKeyedInvalidAttributeException {
			ClientResponseError error = clientResponse.getError();

			if (error.getErrorType() == ClientApiResponseErrorType.OTHER_BAD_RESPONSE_ERROR) {
				List<KeyedInvalidAttribute> invalidAttributes = this.serializeInvalidAttributes(clientResponse);

				if (invalidAttributes.isEmpty() == false) {
					throw new ClientKeyedInvalidAttributeException(invalidAttributes, clientResponse);
				}
			}
		}

		// MARK: Utility
		public List<KeyedInvalidAttribute> serializeInvalidAttributes(ClientApiResponse response) {
			List<KeyedInvalidAttribute> failures = null;

			ClientResponseError error = response.getError();
			Map<String, ClientResponseErrorInfo> errorInfoMap = error.getErrorInfoMap();
			ClientResponseErrorInfo failedPairsInfo = errorInfoMap
			        .get(KeyedInvalidAttributeApiResponseBuilder.ERROR_CODE);

			if (failedPairsInfo != null) {
				failures = this.serializeInvalidAttributes(failedPairsInfo);
			} else {
				failures = Collections.emptyList();
			}

			return failures;
		}

		public List<KeyedInvalidAttribute> serializeInvalidAttributes(ClientResponseErrorInfo keysErrorInfo) {
			JsonNode pairsData = keysErrorInfo.getErrorData();
			return this.serializeInvalidAttributes(pairsData);
		}

		public List<KeyedInvalidAttribute> serializeInvalidAttributes(JsonNode keysArrayNode) {

			ObjectMapper mapper = this.getMapper();

			try {
				KeyedInvalidAttributeData[] data = mapper.treeToValue(keysArrayNode, KeyedInvalidAttributeData[].class);
				return this.mapData(data);
			} catch (JsonProcessingException e) {
				throw new ClientResponseSerializationException(e);
			}
		}

		protected List<KeyedInvalidAttribute> mapData(KeyedInvalidAttributeData[] data) {
			List<KeyedInvalidAttribute> failures = new ArrayList<KeyedInvalidAttribute>();

			for (KeyedInvalidAttributeData entry : data) {
				KeyedInvalidAttributeImpl failure = new KeyedInvalidAttributeImpl(entry);
				failures.add(failure);
			}

			return failures;
		}

	}

	public static class ClientKeyedInvalidAttributeExceptionUtility extends ClientInvalidAttributeExceptionUtility {

		private TypeModelKeyConverter typeKeyConverter;
		private String type;

		public ClientKeyedInvalidAttributeExceptionUtility(TypeModelKeyConverter typeKeyConverter,
		        ObjectMapper mapper,
		        String type) {
			super(mapper);
			this.setTypeKeyConverter(typeKeyConverter);
			this.setType(type);
		}

		public TypeModelKeyConverter getTypeKeyConverter() {
			return this.typeKeyConverter;
		}

		public void setTypeKeyConverter(TypeModelKeyConverter typeKeyConverter) {
			if (typeKeyConverter == null) {
				throw new IllegalArgumentException("typeKeyConverter cannot be null.");
			}

			this.typeKeyConverter = typeKeyConverter;
		}

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			if (type == null) {
				throw new IllegalArgumentException("type cannot be null.");
			}

			this.type = type;
		}

		@Override
		protected List<KeyedInvalidAttribute> mapData(KeyedInvalidAttributeData[] data) {
			TypeModelKeyConverter typeKeyConverter = this.getTypeKeyConverter();
			StringModelKeyConverter keyConverter = typeKeyConverter.getConverterForType(this.getType());
			List<KeyedInvalidAttribute> failures = new ArrayList<KeyedInvalidAttribute>();

			for (KeyedInvalidAttributeData entry : data) {
				String stringKey = entry.getKey();
				ModelKey key = keyConverter.convertSingle(stringKey);

				KeyedInvalidAttributeImpl failure = new KeyedInvalidAttributeImpl(key, entry);
				failures.add(failure);
			}

			return failures;
		}

	}

	@Override
	public String toString() {
		return "ClientKeyedInvalidAttributeException [invalidAttributes=" + this.invalidAttributes + "]";
	}

}
