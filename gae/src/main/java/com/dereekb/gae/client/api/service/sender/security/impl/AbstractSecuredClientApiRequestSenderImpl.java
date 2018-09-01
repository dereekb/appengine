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
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;

/**
 * {@link SecuredClientApiRequestSender} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractSecuredClientApiRequestSenderImpl
        implements SecuredClientApiRequestSender {

	private ClientApiRequestSender sender;
	private ClientRequestSecurity defaultSecurity;

	public AbstractSecuredClientApiRequestSenderImpl(ClientApiRequestSender sender,
	        ClientRequestSecurity defaultSecurity) {
		this.setSender(sender);
		this.setDefaultSecurity(defaultSecurity);
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
	protected abstract ClientRequest buildSecuredRequest(ClientRequest request,
	                                            ClientRequestSecurity security);

}
