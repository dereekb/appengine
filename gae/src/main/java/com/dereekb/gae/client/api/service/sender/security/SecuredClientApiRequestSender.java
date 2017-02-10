package com.dereekb.gae.client.api.service.sender.security;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.sender.ClientApiRequestSender;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;

/**
 * Sends the request with a specified authentication token attached.
 * 
 * @author dereekb
 *
 */
public interface SecuredClientApiRequestSender
        extends ClientApiRequestSender {

	/**
	 * Sends the request using the default security configuration.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public ClientApiResponse sendRequest(ClientRequest request);

	/**
	 * Sends a synchronous HTTP request using the context described by the type.
	 * 
	 * @param request
	 *            {@link ClientRequest}. Never {@code null}.
	 * @param type
	 *            {@link ClientRequestSecurity}. Never {@code null}.
	 * @return {@link ClientApiResponse}. Never {@code null}.
	 * @throws NoSecurityContextException
	 *             thrown if a token could not be accessed.
	 */
	public ClientApiResponse sendRequest(ClientRequest request,
	                                     ClientRequestSecurity sercurity)
	        throws NoSecurityContextException;

}
