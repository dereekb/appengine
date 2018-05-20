package com.dereekb.gae.client.api.auth.system.impl;

import java.io.IOException;

import com.dereekb.gae.client.api.auth.system.ClientSystemLoginTokenService;
import com.dereekb.gae.client.api.auth.system.ClientSystemLoginTokenServiceRequestSender;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationRequest;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationResponse;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenExpiredException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenInvalidException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenInvalidSignatureException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenValidationException;
import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.shared.builder.impl.AbstractSecuredClientModelRequestSender;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestData;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestDataImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestUrlImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenRequest;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenResponse;
import com.dereekb.gae.server.auth.security.system.exception.SystemLoginTokenServiceException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.dereekb.gae.web.api.auth.controller.system.impl.ApiSystemLoginTokenRequest;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link ClientSystemLoginTokenService} implementation.
 *
 * @author dereekb
 *
 */
public class ClientSystemLoginTokenServiceRequestSenderImpl extends AbstractSecuredClientModelRequestSender<SystemLoginTokenRequest, SystemLoginTokenResponse>
        implements ClientSystemLoginTokenServiceRequestSender {

	private static final String DEFAULT_PATH = "/login/auth/system/token";

	private String path = DEFAULT_PATH;

	public ClientSystemLoginTokenServiceRequestSenderImpl(SecuredClientApiRequestSender requestSender)
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

	// MARK: ClientSystemLoginTokenServiceRequestSender

	@Override
	public SystemLoginTokenResponse makeSystemToken(SystemLoginTokenRequest request) throws SystemLoginTokenServiceException {
		try {
			return this.sendRequest(request, ClientRequestSecurityImpl.none()).getSerializedResponse();
		} catch (NotClientApiResponseException | ClientRequestFailureException e) {
			throw new SystemLoginTokenServiceException(e);
		}
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
	public ClientRequest buildClientRequest(SystemLoginTokenRequest request) {
		ClientRequestUrl url = new ClientRequestUrlImpl(this.path);
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.POST);

		ApiSystemLoginTokenRequest apiRequest = new ApiSystemLoginTokenRequest(request);

		ClientRequestData requestData = ClientRequestDataImpl.make(this.getObjectMapper(), apiRequest);
		clientRequest.setData(requestData);

		return clientRequest;
	}

	@Override
	public SystemLoginTokenResponse serializeResponseData(SystemLoginTokenRequest request,
	                                                 ClientApiResponse response)
	        throws ClientResponseSerializationException {
		return new ClientSystemLoginTokenServiceResponse(request, response);
	}

	private class ClientSystemLoginTokenServiceResponse extends AbstractSerializedResponse
	        implements SystemLoginTokenResponse {

		private final String encodedLoginToken;
		private transient LoginToken loginToken;

		public ClientSystemLoginTokenServiceResponse(SystemLoginTokenRequest request, ClientApiResponse response) {
			super(response);
			this.encodedLoginToken = this.serializeEncodedLoginToken();
		}

		// MARK: SystemTokenResponse
		@Override
		public String getEncodedLoginToken() {
			return this.encodedLoginToken;
		}

		private String serializeEncodedLoginToken() throws ClientResponseSerializationException {
			JsonNode responseData = this.response.getApiResponseJsonNode();

			try {
				LoginTokenPair pair = ObjectMapperUtilityBuilderImpl.builder(getObjectMapper()).make().map(responseData,
				        LoginTokenPair.class);
				return pair.getEncodedLoginToken();
			} catch (IllegalArgumentException | IOException e) {
				throw new ClientResponseSerializationException(e);
			}
		}

		@Override
		public LoginToken getLoginToken() {
			if (this.loginToken == null) {
				this.loginToken = this.decodeLoginToken();
			}

			return this.loginToken;
		}

		private LoginToken decodeLoginToken() {
			// TODO: Either build using the available decoder, set without a
			// signature, or
			throw new UnsupportedOperationException();
		}

	}

}
