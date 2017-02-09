package com.dereekb.gae.client.api.service.sender.impl;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.sender.ClientRequestSecurityContextType;
import com.dereekb.gae.client.api.service.sender.SecuredClientRequestSender;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;

public class SecuredClientRequestSenderImpl
        implements SecuredClientRequestSender {

	// MARK: SecuredClientRequestSender
	@Override
	public ClientResponse sendRequest(ClientRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientResponse sendRequest(ClientRequest request,
	                                  String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientResponse sendRequest(ClientRequest request,
	                                  EncodedLoginToken token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientResponse sendRequest(ClientRequest request,
	                                  ClientRequestSecurityContextType type)
	        throws NoSecurityContextException {
		// TODO Auto-generated method stub
		return null;
	}

}
