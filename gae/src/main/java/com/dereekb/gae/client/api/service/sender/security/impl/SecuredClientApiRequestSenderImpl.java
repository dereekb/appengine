package com.dereekb.gae.client.api.service.sender.security.impl;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.sender.ClientApiRequestSender;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurityContextType;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenFactory;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;

/**
 * {@link SecuredClientApiRequestSender} implementation.
 *
 * @author dereekb
 *
 */
public class SecuredClientApiRequestSenderImpl extends AbstractSecuredClientApiRequestSenderImpl {

	private SystemLoginTokenFactory systemTokenFactory;

	public SecuredClientApiRequestSenderImpl(ClientApiRequestSender sender,
	        SystemLoginTokenFactory systemTokenFactory) {
		this(sender, new ClientRequestSecurityImpl(), systemTokenFactory);
	}

	public SecuredClientApiRequestSenderImpl(ClientApiRequestSender sender,
	        ClientRequestSecurity defaultSecurity,
	        SystemLoginTokenFactory systemTokenFactory) {
		super(sender, defaultSecurity);
		this.setSystemTokenFactory(systemTokenFactory);
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
	// MARK: Internal
	@Override
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

	@Override
	public String toString() {
		return "SecuredClientApiRequestSenderImpl [systemTokenFactory=" + this.systemTokenFactory + ", getSender()="
		        + this.getSender() + ", getDefaultSecurity()=" + this.getDefaultSecurity() + "]";
	}

}
