package com.dereekb.gae.client.api.auth.token.impl;

import java.util.Map;

import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationRequest;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationResponse;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationService;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationServiceRequestSender;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenExpiredException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenInvalidException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenInvalidSignatureException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenValidationException;
import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.shared.builder.impl.AbstractSecuredClientModelRequestSender;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestUrlImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.dereekb.gae.utilities.misc.parameters.impl.ParametersImpl;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link ClientLoginTokenValidationService} implementation.
 *
 * @author dereekb
 *
 */
public class ClientLoginTokenValidationServiceRequestSenderImpl extends AbstractSecuredClientModelRequestSender<ClientLoginTokenValidationRequest, ClientLoginTokenValidationResponse>
        implements ClientLoginTokenValidationServiceRequestSender {

	private static final String DEFAULT_PATH = "/login/auth/token/validate";

	private String path = DEFAULT_PATH;

	public ClientLoginTokenValidationServiceRequestSenderImpl(SecuredClientApiRequestSender requestSender)
	        throws IllegalArgumentException {
		super(requestSender);
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

	// MARK: ClientLoginTokenValidationServiceRequestSender
	@Override
	public ClientLoginTokenValidationResponse validateToken(ClientLoginTokenValidationRequest request)
	        throws ClientLoginTokenExpiredException,
	            ClientLoginTokenInvalidException,
	            ClientLoginTokenInvalidSignatureException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException {

		SerializedClientApiResponse<ClientLoginTokenValidationResponse> clientResponse = null;

		try {
			clientResponse = this.sendRequest(request, ClientRequestSecurityImpl.none());
		} catch (ClientAuthenticationException e) {
			if (ClientApiResponse.class.isAssignableFrom(e.getResponse().getClass())) {
				ClientApiResponse clientApiResponse = (ClientApiResponse) e.getResponse();
				ClientLoginTokenValidationException.assertNoTokenValidationException(clientApiResponse);
			}

			throw new ClientLoginTokenValidationException(null);
		}

		this.assertSuccessfulResponse(request, clientResponse);
		return clientResponse.getSerializedResponse();
	}

	public void assertSuccessfulResponse(ClientLoginTokenValidationRequest request,
	                                     SerializedClientApiResponse<ClientLoginTokenValidationResponse> clientResponse)
	        throws ClientLoginTokenExpiredException,
	            ClientLoginTokenInvalidException,
	            ClientLoginTokenInvalidSignatureException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException {
		if (clientResponse.isSuccessful() == false) {
			ClientLoginTokenValidationException.assertNoTokenValidationException(clientResponse);
			ClientIllegalArgumentException.assertNoIllegalArgumentException(clientResponse);
			throw new ClientRequestFailureException(clientResponse);
		}
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public ClientRequest buildClientRequest(ClientLoginTokenValidationRequest request) {
		ClientRequestUrl url = new ClientRequestUrlImpl(this.path);
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.POST);

		clientRequest.setContentType("application/x-www-form-urlencoded");

		// URL-encoded parameters
		ParametersImpl parameters = new ParametersImpl();

		parameters.addEntry("token", request.getToken());

		String content = request.getContent();

		if (content != null) {
			parameters.addEntry("content", content);
		}

		String signature = request.getSignature();

		if (signature != null) {
			parameters.addEntry("signature", signature);
		}

		parameters.addObjectParameter("quick", !ValueUtility.valueOf(request.getIncludeClaims()));
		clientRequest.setParameters(parameters);

		return clientRequest;
	}

	@Override
	public ClientLoginTokenValidationResponse serializeResponseData(ClientLoginTokenValidationRequest request,
	                                                                ClientApiResponse response)
	        throws ClientResponseSerializationException {
		return new ClientLoginTokenValidationResponseImpl(request, response);
	}

	private class ClientLoginTokenValidationResponseImpl
	        implements ClientLoginTokenValidationResponse {

		private transient Map<String, String> claimsMap;

		private final ClientLoginTokenValidationRequest request;
		private final ClientApiResponse response;

		public ClientLoginTokenValidationResponseImpl(ClientLoginTokenValidationRequest request,
		        ClientApiResponse response) {
			super();
			this.request = request;
			this.response = response;
		}

		// MARK: ClientLoginTokenValidationResponse
		@Override
		public Map<String, String> getClaimsMap() throws UnsupportedOperationException {
			if (this.claimsMap == null) {
				if (!this.request.getIncludeClaims()) {
					throw new UnsupportedOperationException("Claims were not requested.");
				}

				ClientApiResponseData data = this.response.getPrimaryData();
				JsonNode node = data.getJsonNode();
				this.claimsMap = ObjectMapperUtilityBuilderImpl.SINGLETON.make().mapToStringMap(node);
			}

			return this.claimsMap;
		}

	}

}
