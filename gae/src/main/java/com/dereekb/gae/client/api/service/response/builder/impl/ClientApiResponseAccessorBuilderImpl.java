package com.dereekb.gae.client.api.service.response.builder.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.service.response.ClientApiResponseAccessor;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.response.builder.ClientApiResponseAccessorBuilder;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.NoClientResponseDataException;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.utilities.misc.keyed.utility.KeyedUtility;
import com.dereekb.gae.utilities.web.error.ErrorInfo;
import com.dereekb.gae.utilities.web.error.impl.ErrorInfoImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientApiResponseAccessorBuilder} implementation.
 * 
 * @author dereekb
 *
 */
public class ClientApiResponseAccessorBuilderImpl
        implements ClientApiResponseAccessorBuilder {

	public static final ClientApiResponseAccessorBuilder SINGLETON = new ClientApiResponseAccessorBuilderImpl();

	public static final String SUCCESS_KEY = "success";
	public static final String PRIMARY_DATA_KEY = "data";
	public static final String INCLUDED_DATA_KEY = "included";
	public static final String ERRORS_KEY = "errors";

	private ObjectMapper objectMapper;

	private String successKey = SUCCESS_KEY;
	private String primaryDataKey = PRIMARY_DATA_KEY;
	private String includedDataKey = INCLUDED_DATA_KEY;
	private String errorsDataKey = ERRORS_KEY;

	public ClientApiResponseAccessorBuilderImpl() {
		this(new ObjectMapper());
	}

	public ClientApiResponseAccessorBuilderImpl(ObjectMapper objectMapper) {
		this.setObjectMapper(objectMapper);
	}

	public ObjectMapper getObjectMapper() {
		return this.objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		if (objectMapper == null) {
			throw new IllegalArgumentException("objectMapper cannot be null.");
		}

		this.objectMapper = objectMapper;
	}

	@Override
	public ClientApiResponseAccessor buildAccessor(ClientResponse response) throws NotClientApiResponseException {
		String jsonData = response.getResponseData();

		if (jsonData == null) {
			throw new NotClientApiResponseException("No body/data available on response.");
		}

		return this.buildAccessor(jsonData, response.getStatus());
	}

	@Override
	public ClientApiResponseAccessor buildAccessor(String responseData) {
		return this.buildAccessor(responseData, 200);
	}

	@Override
	public ClientApiResponseAccessor buildAccessor(String responseData,
	                                               int statusCode)
	        throws NotClientApiResponseException {
		JsonNode jsonNode;

		try {
			jsonNode = this.objectMapper.readTree(responseData);
		} catch (IOException e) {
			throw new NotClientApiResponseException("Client Json Parsing Failed.");
		}

		return this.buildAccessor(jsonNode, statusCode);
	}

	protected ClientApiResponseAccessor buildAccessor(JsonNode jsonNode,
	                                                  int statusCode) {
		return new ClientApiResponseAccessorImpl(jsonNode, statusCode);
	}

	protected class ClientApiResponseAccessorImpl
	        implements ClientApiResponseAccessor {

		private final JsonNode jsonNode;

		private final int statusCode;

		private ClientApiResponseData primaryData;
		private Map<String, ClientApiResponseData> includedData;
		private ClientResponseError errors;

		public ClientApiResponseAccessorImpl(JsonNode jsonNode, int statusCode) throws IllegalArgumentException {
			if (jsonNode == null) {
				throw new IllegalArgumentException("Json cannot be null.");
			}

			this.jsonNode = jsonNode;
			this.statusCode = statusCode;
		}

		@Override
		public boolean getSuccess() {
			return this.jsonNode.get(ClientApiResponseAccessorBuilderImpl.this.successKey).asBoolean(true);
		}

		@Override
		public ClientApiResponseData getPrimaryData() throws NoClientResponseDataException {
			if (this.primaryData == null) {
				if (this.jsonNode.has(ClientApiResponseAccessorBuilderImpl.this.primaryDataKey)) {
					JsonNode dataNode = this.jsonNode.get(ClientApiResponseAccessorBuilderImpl.this.primaryDataKey);
					this.primaryData = new JsonClientApiResponseData(dataNode);
				} else {
					throw new NoClientResponseDataException();
				}
			}

			return this.primaryData;
		}

		@Override
		public Map<String, ClientApiResponseData> getIncludedData() {
			if (this.includedData == null) {
				List<ClientApiResponseData> data = this.getIncludedDataArray();
				this.includedData = KeyedUtility.toMap(data);
			}

			return this.includedData;
		}

		@Override
		public ClientResponseError getError() {
			if (this.errors == null) {
				JsonNode errors = this.jsonNode.get(ClientApiResponseAccessorBuilderImpl.this.errorsDataKey);
				this.errors = new JsonClientErrorsData(errors, this.statusCode);
			}

			return this.errors;
		}

		// MARK: Internal
		private List<ClientApiResponseData> getIncludedDataArray() {
			List<ClientApiResponseData> list = new ArrayList<ClientApiResponseData>();

			JsonNode included = this.jsonNode.get(ClientApiResponseAccessorBuilderImpl.this.includedDataKey);

			if (included != null) {
				for (JsonNode node : included) {
					list.add(new JsonClientApiResponseData(node));
				}
			}

			return list;
		}

	}

	/**
	 * 
	 * @author dereekb
	 *
	 */
	private class JsonClientApiResponseData
	        implements ClientApiResponseData {

		public static final String DATA_TYPE_KEY = "type";
		public static final String DATA_KEY = "data";

		private final JsonNode dataNode;

		public JsonClientApiResponseData(JsonNode dataNode) {
			this.dataNode = dataNode;
		}

		// MARK: ClientApiResponseData
		@Override
		public String getDataType() {
			return this.dataNode.get(DATA_TYPE_KEY).asText();
		}

		@Override
		public JsonNode getDataJsonNode() {
			return this.dataNode.get(DATA_KEY);
		}

		// MARK: Keyed
		@Override
		public String getKeyValue() {
			return this.getDataType();
		}

	}

	/**
	 * 
	 * @author dereekb
	 * 
	 * @see ErrorInfo
	 */
	private class JsonClientErrorsData
	        implements ClientResponseError {

		private static final String DATA_KEY = "data";

		private final JsonNode errorsNode;
		private final int statusCode;

		private List<ClientResponseErrorInfo> errors = null;

		public JsonClientErrorsData(JsonNode errors, int statusCode) {
			this.errorsNode = errors;
			this.statusCode = statusCode;
		}

		// MARK: ClientResponseError
		@Override
		public ClientApiResponseErrorType getErrorType() {
			return ClientApiResponseErrorType.typeForCode(this.statusCode);
		}

		@Override
		public List<ClientResponseErrorInfo> getErrorInfo() {
			if (this.errors == null) {
				this.errors = new ArrayList<ClientResponseErrorInfo>();

				try {
					for (JsonNode node : this.errorsNode) {
						ClientResponseErrorInfoImpl info = ClientApiResponseAccessorBuilderImpl.this.objectMapper
						        .treeToValue(node, ClientResponseErrorInfoImpl.class);

						if (node.has(DATA_KEY)) {
							JsonNode dataNode = node.get(DATA_KEY);
							info.setErrorData(dataNode);
						}

						this.errors.add(info);
					}
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}

			return this.errors;
		}

		@Override
		public Map<String, ClientResponseErrorInfo> getErrorInfoMap() {
			return KeyedUtility.toMap(this.getErrorInfo());
		}

	}

	private class ClientResponseErrorInfoImpl extends ErrorInfoImpl
	        implements ClientResponseErrorInfo {

		private JsonNode errorData;

		@Override
		public JsonNode getErrorData() {
			return this.errorData;
		}

		public void setErrorData(JsonNode errorData) {
			this.errorData = errorData;
		}

	}

	@Override
	public String toString() {
		return "ClientApiResponseAccessorBuilderImpl [objectMapper=" + this.objectMapper + "]";
	}

}
