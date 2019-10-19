package com.dereekb.gae.client.api.auth.model.context.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.client.api.auth.model.context.ClientModelRolesLoginTokenContextRequest;
import com.dereekb.gae.client.api.auth.model.context.ClientModelRolesLoginTokenContextResponse;
import com.dereekb.gae.client.api.auth.model.context.ClientModelRolesLoginTokenContextService;
import com.dereekb.gae.client.api.auth.model.context.ClientModelRolesLoginTokenContextServiceRequestSender;
import com.dereekb.gae.client.api.auth.model.roles.ClientModelRolesRequest;
import com.dereekb.gae.client.api.auth.model.roles.ClientModelRolesResponseData;
import com.dereekb.gae.client.api.auth.model.roles.ClientModelRolesService;
import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException.ClientAtomicOperationExceptionUtility;
import com.dereekb.gae.client.api.model.shared.builder.impl.AbstractSecuredClientModelRequestSender;
import com.dereekb.gae.client.api.model.shared.utility.ClientModelKeySerializer;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestData;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestDataImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestUrlImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.web.api.auth.controller.model.context.impl.ApiLoginTokenModelContextRequest;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesResponseData;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesTypedKeysRequest;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientModelRolesLoginTokenContextService} implementation.
 *
 * @author dereekb
 *
 * @deprecated Replaced by {@link ClientModelRolesService}.
 */
@Deprecated
public class ClientModelRolesContextServiceRequestSenderImpl extends AbstractSecuredClientModelRequestSender<ClientModelRolesLoginTokenContextRequest, ClientModelRolesLoginTokenContextResponse>
        implements ClientModelRolesLoginTokenContextServiceRequestSender {

	private static final String DEFAULT_PATH = "/login/auth/model";
	private static final String TOKEN_PATH_SUFFIX = "/token";

	private String path = DEFAULT_PATH;

	private TypeModelKeyConverter keyTypeConverter;

	private transient ClientModelKeySerializer keySerializer;
	private transient ClientAtomicOperationExceptionUtility atomicOperationUtility;

	public ClientModelRolesContextServiceRequestSenderImpl(TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(requestSender);
		this.setKeyTypeConverter(keyTypeConverter);
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		if (path == null) {
			throw new IllegalArgumentException("path cannot be null.");
		}

		this.path = path;
	}

	public TypeModelKeyConverter getKeyTypeConverter() {
		return this.keyTypeConverter;
	}

	public void setKeyTypeConverter(TypeModelKeyConverter keyTypeConverter) {
		if (keyTypeConverter == null) {
			throw new IllegalArgumentException("keyTypeConverter cannot be null.");
		}

		this.keyTypeConverter = keyTypeConverter;
		this.keySerializer = new ClientModelKeySerializer(keyTypeConverter);
		this.atomicOperationUtility = new ClientAtomicOperationExceptionUtility(this.keySerializer);
	}

	// MARK: ClientModelRolesContextServiceRequestSender
	public ClientModelRolesResponseData getRolesForModels(ClientModelRolesRequest request,
	                                                      ClientRequestSecurity security)
	        throws ClientIllegalArgumentException,
	            ClientAtomicOperationException,
	            ClientRequestFailureException {
		ClientModelRolesLoginTokenContextRequestImpl contextRequest = new ClientModelRolesLoginTokenContextRequestImpl(
		        request);
		contextRequest.setMakeContext(false);
		contextRequest.setIncludeRoles(true);
		return this.getContextForModels(contextRequest).getRolesData();
	}

	@Override
	public ClientModelRolesLoginTokenContextResponse getContextForModels(ClientModelRolesLoginTokenContextRequest request)
	        throws ClientIllegalArgumentException,
	            ClientAtomicOperationException,
	            ClientRequestFailureException {
		return this.getContextForModels(request, null);
	}

	@Override
	public ClientModelRolesLoginTokenContextResponse getContextForModels(ClientModelRolesLoginTokenContextRequest request,
	                                                                     ClientRequestSecurity security)
	        throws ClientIllegalArgumentException,
	            ClientAtomicOperationException,
	            ClientRequestFailureException {

		SerializedClientApiResponse<ClientModelRolesLoginTokenContextResponse> clientResponse = this
		        .sendRequest(request, security);
		this.assertSuccessfulResponse(request, clientResponse);
		return clientResponse.getSerializedResponse();
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public ClientRequest buildClientRequest(ClientModelRolesLoginTokenContextRequest request) {
		ClientRequestUrl url = this.makeTokenRequestUrl();
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.PUT);

		ClientRequestData data = this.makeRequestData(request);
		clientRequest.setData(data);

		return clientRequest;
	}

	@Override
	public ClientModelRolesLoginTokenContextResponse serializeResponseData(ClientModelRolesLoginTokenContextRequest request,
	                                                                       ClientApiResponse response)
	        throws ClientResponseSerializationException {
		return new ClientModelRolesLoginTokenContextResponseImpl(request, response);
	}

	// MARK: Utility
	public ClientRequestUrl makeTokenRequestUrl() {
		return new ClientRequestUrlImpl(this.path + TOKEN_PATH_SUFFIX);
	}

	public ClientRequestData makeRequestData(ClientModelRolesLoginTokenContextRequest request) {

		ObjectMapper mapper = this.getObjectMapper();

		ApiLoginTokenModelContextRequest tokenRequest = new ApiLoginTokenModelContextRequest();

		tokenRequest.setAtomic(request.isAtomic());
		tokenRequest.setExpirationTime(request.getExpirationTime());
		tokenRequest.setMakeContext(request.getMakeContext());
		tokenRequest.setIncludeRoles(request.getIncludeRoles());

		List<ApiModelRolesTypedKeysRequest> contexts = request.getRequestedContexts();
		tokenRequest.setContexts(contexts);

		ClientRequestDataImpl requestData = ClientRequestDataImpl.make(mapper, tokenRequest);
		return requestData;
	}

	public void assertSuccessfulResponse(ClientModelRolesLoginTokenContextRequest request,
	                                     SerializedClientApiResponse<ClientModelRolesLoginTokenContextResponse> clientResponse)
	        throws ClientRequestFailureException {
		if (clientResponse.isSuccessful() == false) {
			this.atomicOperationUtility.assertNoAtomicOperationError(clientResponse);
			ClientIllegalArgumentException.assertNoIllegalArgumentException(clientResponse);
			throw new ClientRequestFailureException(clientResponse);
		}
	}

	private class ClientModelRolesLoginTokenContextResponseImpl
	        implements ClientModelRolesLoginTokenContextResponse {

		private transient boolean serializedToken = false;
		private transient boolean serializedData = false;

		private transient LoginTokenPair loginTokenPair;
		private transient ClientModelRolesResponseData responseData;

		private final ClientModelRolesLoginTokenContextRequest request;
		private final ClientApiResponse response;

		public ClientModelRolesLoginTokenContextResponseImpl(ClientModelRolesLoginTokenContextRequest request,
		        ClientApiResponse response) {
			super();
			this.request = request;
			this.response = response;
		}

		// MARK: ClientModelRolesLoginTokenContextResponse
		@Override
		public LoginTokenPair getLoginTokenPair() {

			if (!this.serializedToken) {
				if (this.request.getMakeContext()) {
					this.loginTokenPair = this.serializeLoginToken();
				}

				this.serializedToken = true;
			}

			return this.loginTokenPair;
		}

		private LoginTokenPair serializeLoginToken() {
			ClientApiResponseData data = this.response.getPrimaryData();
			ObjectMapper mapper = ClientModelRolesContextServiceRequestSenderImpl.this.getObjectMapper();

			JsonNode jsonNode = data.getJsonNode();

			try {
				return mapper.treeToValue(jsonNode, LoginTokenPair.class);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public ClientModelRolesResponseData getRolesData() {

			if (!this.serializedData) {
				if (ValueUtility.valueOf(this.request.getIncludeRoles())) {
					ApiModelRolesResponseData data = this.serializeRolesResponseData();
					this.responseData = new ClientModelRolesResponseDataImpl(data);
				}

				this.serializedData = true;
			}

			return this.responseData;
		}

		private ApiModelRolesResponseData serializeRolesResponseData() {
			Map<String, ClientApiResponseData> included = this.response.getIncludedData();

			ClientApiResponseData clientRolesData = included.get(ApiModelRolesResponseData.DATA_TYPE);

			ObjectMapper mapper = ClientModelRolesContextServiceRequestSenderImpl.this.getObjectMapper();
			JsonNode jsonNode = clientRolesData.getJsonNode();

			try {
				return mapper.treeToValue(jsonNode, ApiModelRolesResponseData.class);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}

	}

	private class ClientModelRolesResponseDataImpl
	        implements ClientModelRolesResponseData {

		private final ApiModelRolesResponseData rolesData;
		private CaseInsensitiveMap<Map<ModelKey, Set<String>>> rolesMap = new CaseInsensitiveMap<Map<ModelKey, Set<String>>>();

		public ClientModelRolesResponseDataImpl(ApiModelRolesResponseData rolesData) {
			this.rolesData = rolesData;
		}

		// MARK: ClientModelRolesResponseData
		@Override
		public Map<String, Map<String, Set<String>>> getRawMap() {
			return this.rolesData.getResults();
		}

		@Override
		public Map<ModelKey, Set<String>> getRolesForType(String type) {
			Map<ModelKey, Set<String>> map = this.rolesMap.get(type);

			if (map == null) {
				Map<String, Map<String, Set<String>>> raw = this.getRawMap();
				map = this.buildRolesMapForType(type, raw);
				this.rolesMap.put(type, map);
			}

			return map;
		}

		// MARK: Internal
		protected Map<ModelKey, Set<String>> buildRolesMapForType(String type,
		                                                          Map<String, Map<String, Set<String>>> raw) {
			Map<String, Set<String>> typeRaw = raw.get(type);
			Map<ModelKey, Set<String>> map = new HashMap<ModelKey, Set<String>>();

			if (typeRaw != null) {
				for (Entry<String, Set<String>> entry : typeRaw.entrySet()) {
					String key = entry.getKey();
					Set<String> roles = entry.getValue();

					ModelKey modelKey = ClientModelRolesContextServiceRequestSenderImpl.this.keyTypeConverter
					        .convertKey(type, key);
					map.put(modelKey, roles);
				}
			} else {
				map = Collections.emptyMap();
			}

			return map;
		}

	}

}
