package com.dereekb.gae.client.api.service.sender.security;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
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
	 * {@inheritDoc}
	 * 
	 * @throws NoSecurityContextException
	 *             thrown if a token could not be accessed.
	 * @throws ClientAuthenticationException
	 *             thrown if the request fails due to authentication reasons.
	 */
	@Override
	public ClientApiResponse sendRequest(ClientRequest request)
	        throws NoSecurityContextException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException;

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
	 * @throws ClientConnectionException
	 *             thrown if the connection encounters an error.
	 * @throws ClientAuthenticationException
	 *             thrown if the request fails due to authentication reasons.
	 */
	public ClientApiResponse sendRequest(ClientRequest request,
	                                     ClientRequestSecurity sercurity)
	        throws NoSecurityContextException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException;

}
