package com.dereekb.gae.client.api.service.sender.security.impl;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.sender.ClientRequestSender;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurityContextType;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientRequestSender;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenFactory;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;

/**
 * {@link SecuredClientRequestSender} implementation.
 * 
 * @author dereekb
 *
 */
public class SecuredClientRequestSenderImpl
        implements SecuredClientRequestSender {

	private ClientRequestSender sender;
	private ClientRequestSecurity defaultSecurity;

	private SystemLoginTokenFactory systemTokenFactory;

	public SecuredClientRequestSenderImpl(ClientRequestSender sender,
	        ClientRequestSecurity defaultSecurity,
	        SystemLoginTokenFactory systemTokenFactory) {
		this.setSender(sender);
		this.setDefaultSecurity(defaultSecurity);
		this.setSystemTokenFactory(systemTokenFactory);
	}

	public ClientRequestSender getSender() {
		return this.sender;
	}

	public void setSender(ClientRequestSender sender) {
		if (sender == null) {
			throw new IllegalArgumentException("sender cannot be null.");
		}

		this.sender = sender;
	}

	public ClientRequestSecurity getDefaultSecurity() {
		return this.defaultSecurity;
	}

	public void setDefaultSecurity(ClientRequestSecurity defaultSecurity) {
		if (defaultSecurity == null) {
			throw new IllegalArgumentException("defaultSecurity cannot be null.");
		}

		this.defaultSecurity = defaultSecurity;
	}

	public SystemLoginTokenFactory getSystemTokenFactory() {
		return this.systemTokenFactory;
	}

	public void setSystemTokenFactory(SystemLoginTokenFactory systemTokenFactory) {
		if (systemTokenFactory == null) {
			throw new IllegalArgumentException("systemTokenFactory cannot be null.");
		}

		this.systemTokenFactory = systemTokenFactory;
	}

	// MARK: SecuredClientRequestSender
	@Override
	public ClientResponse sendRequest(ClientRequest request) {
		return this.sendRequest(request, this.defaultSecurity);
	}

	@Override
	public ClientResponse sendRequest(ClientRequest request,
	                                  ClientRequestSecurity security)
	        throws NoSecurityContextException {

		if (security == null) {
			throw new IllegalArgumentException("Security cannot be null.");
		}

		ClientRequest securedRequest = this.buildSecuredRequest(request, security);
		return this.sender.sendRequest(securedRequest);
	}

	// MARK: Internal
	protected ClientRequest buildSecuredRequest(ClientRequest request,
	                                            ClientRequestSecurity security) {
		ClientRequest securedRequest = null;
		EncodedLoginToken overrideToken = security.getOverrideToken();
		String tokenString = null;

		if (overrideToken == null) {
			ClientRequestSecurityContextType type = security.getSecurityContextType();

			switch (type) {
				case CURRENT:
					DecodedLoginToken currentToken = LoginSecurityContext.getAuthentication().getCredentials();
					tokenString = currentToken.getEncodedLoginToken();
					break;
				case SYSTEM:
					tokenString = this.systemTokenFactory.makeTokenString();
					break;
				case NONE:
					// No token.
					break;
				default:
					break;
			}
		} else {
			tokenString = overrideToken.getEncodedLoginToken();
		}

		if (tokenString != null) {
			securedRequest = new SecuredClientRequestImpl(tokenString, request);
		} else {
			securedRequest = request;
		}

		return securedRequest;
	}

}
