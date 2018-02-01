package com.dereekb.gae.client.api.service.sender.security.impl;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.sender.ClientApiRequestSender;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurityContextType;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenFactory;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;

/**
 * {@link SecuredClientApiRequestSender} implementation.
 *
 * @author dereekb
 *
 */
public class SecuredClientApiRequestSenderImpl
        implements SecuredClientApiRequestSender {

	private ClientApiRequestSender sender;
	private ClientRequestSecurity defaultSecurity;

	private SystemLoginTokenFactory systemTokenFactory;

	public SecuredClientApiRequestSenderImpl(ClientApiRequestSender sender,
	        SystemLoginTokenFactory systemTokenFactory) {
		this(sender, new ClientRequestSecurityImpl(), systemTokenFactory);
	}

	public SecuredClientApiRequestSenderImpl(ClientApiRequestSender sender,
	        ClientRequestSecurity defaultSecurity,
	        SystemLoginTokenFactory systemTokenFactory) {
		this.setSender(sender);
		this.setDefaultSecurity(defaultSecurity);
		this.setSystemTokenFactory(systemTokenFactory);
	}

	public ClientApiRequestSender getSender() {
		return this.sender;
	}

	public void setSender(ClientApiRequestSender sender) {
		if (sender == null) {
			throw new IllegalArgumentException("Sender cannot be null.");
		}

		this.sender = sender;
	}

	public ClientRequestSecurity getDefaultSecurity() {
		return this.defaultSecurity;
	}

	public void setDefaultSecurity(ClientRequestSecurity defaultSecurity) {
		if (defaultSecurity == null) {
			throw new IllegalArgumentException("DefaultSecurity cannot be null.");
		}

		this.defaultSecurity = defaultSecurity;
	}

	public SystemLoginTokenFactory getSystemTokenFactory() {
		return this.systemTokenFactory;
	}

	public void setSystemTokenFactory(SystemLoginTokenFactory systemTokenFactory) {
		if (systemTokenFactory == null) {
			throw new IllegalArgumentException("SystemTokenFactory cannot be null.");
		}

		this.systemTokenFactory = systemTokenFactory;
	}

	// MARK: SecuredClientApiRequestSender
	@Override
	public ClientApiResponse sendRequest(ClientRequest request)
	        throws NoSecurityContextException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {
		return this.sendRequest(request, this.defaultSecurity);
	}

	@Override
	public ClientApiResponse sendRequest(ClientRequest request,
	                                     ClientRequestSecurity security)
	        throws NoSecurityContextException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		if (security == null) {
			throw new IllegalArgumentException("Security cannot be null.");
		}

		ClientRequest securedRequest = this.buildSecuredRequest(request, security);
		ClientApiResponse response = this.sender.sendRequest(securedRequest);

		this.assertSuccessfulResponse(response);

		return response;
	}

	protected void assertSuccessfulResponse(ClientApiResponse response) throws ClientAuthenticationException {
		if (response.isSuccessful() == false) {
			this.assertNoAuthenticationErrors(response);
		}
	}

	protected void assertNoAuthenticationErrors(ClientApiResponse response) throws ClientAuthenticationException {
		ClientResponseError error = response.getError();

		if (error.getErrorType() == ClientApiResponseErrorType.AUTHENTICATION_ERROR) {
			throw new ClientAuthenticationException(response);
		}
	}

	// MARK: Internal
	protected ClientRequest buildSecuredRequest(ClientRequest request,
	                                            ClientRequestSecurity security) {
		ClientRequest securedRequest = null;
		EncodedLoginToken loginToken = security.getOverrideToken();

		if (loginToken == null) {
			loginToken = this.getTokenForSecurity(security);
		}

		if (loginToken != null) {
			securedRequest = new SecuredClientRequestImpl(loginToken, request);
		} else {
			securedRequest = request;
		}

		return securedRequest;
	}

	protected EncodedLoginToken getTokenForSecurity(ClientRequestSecurity security) throws NullPointerException {
		EncodedLoginToken token = null;
		ClientRequestSecurityContextType type = security.getSecurityContextType();

		switch (type) {
			case CURRENT:
				token = LoginSecurityContext.getAuthentication().getCredentials();
				break;
			case SYSTEM:
				token = this.systemTokenFactory.makeSystemToken();
				break;
			case OVERRIDE:
				token = security.getOverrideToken();

				if (token == null) {
					throw new NullPointerException("Expected an override token.");
				}
				break;
			case NONE:
				// No token.
				break;
			default:
				break;
		}

		return token;
	}

}
