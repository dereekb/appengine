package com.dereekb.gae.client.api.service.sender;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;

/**
 * Sends the request with a specified token attached.
 * 
 * @author dereekb
 *
 */
public interface SecuredClientRequestSender
        extends ClientRequestSender {

	/**
	 * Sends the request using a default configuration.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public ClientResponse sendRequest(ClientRequest request);

	/**
	 * Sends a synchronous HTTP request, authenticated with input token.
	 * 
	 * @param request
	 *            {@link ClientRequest}. Never {@code null}.
	 * @param token
	 *            {@link String} token. Never {@code null}.
	 * @return {@link ClientResponse}. Never {@code null}.
	 */
	public ClientResponse sendRequest(ClientRequest request,
	                                  String token);

	/**
	 * Sends a synchronous HTTP request, authenticated with input token.
	 * 
	 * @param request
	 *            {@link ClientRequest}. Never {@code null}.
	 * @param token
	 *            {@link EncodedLoginToken}. Never {@code null}.
	 * @return {@link ClientResponse}. Never {@code null}.
	 */
	public ClientResponse sendRequest(ClientRequest request,
	                                  EncodedLoginToken token);

	/**
	 * Sends a synchronous HTTP request using the context described by the type.
	 * 
	 * A type of {@link ClientRequestSecurityContextType#SYSTEM} will use a
	 * system authentication token when the request is made, and a
	 * {@link ClientRequestSecurityContextType#CURRENT} will attempt to use the
	 * current token authentication.
	 * 
	 * @param request
	 *            {@link ClientRequest}. Never {@code null}.
	 * @param type
	 *            {@link ClientRequestSecurityContextType}. Never {@code null}.
	 * @return {@link ClientResponse}. Never {@code null}.
	 * @throws NoSecurityContextException
	 *             thrown if a token could not be accessed.
	 */
	public ClientResponse sendRequest(ClientRequest request,
	                                  ClientRequestSecurityContextType type)
	        throws NoSecurityContextException;

}
