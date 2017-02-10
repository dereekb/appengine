package com.dereekb.gae.client.api.service.sender.security;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.sender.ClientRequestSender;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;

/**
 * Sends the request with a specified authentication token attached.
 * 
 * @author dereekb
 *
 */
public interface SecuredClientRequestSender
        extends ClientRequestSender {

	/**
	 * Sends the request using the default security configuration.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public ClientResponse sendRequest(ClientRequest request);

	/**
	 * Sends a synchronous HTTP request using the context described by the type.
	 * 
	 * @param request
	 *            {@link ClientRequest}. Never {@code null}.
	 * @param type
	 *            {@link ClientRequestSecurity}. Never {@code null}.
	 * @return {@link ClientResponse}. Never {@code null}.
	 * @throws NoSecurityContextException
	 *             thrown if a token could not be accessed.
	 */
	public ClientResponse sendRequest(ClientRequest request,
	                                  ClientRequestSecurity sercurity)
	        throws NoSecurityContextException;

}
