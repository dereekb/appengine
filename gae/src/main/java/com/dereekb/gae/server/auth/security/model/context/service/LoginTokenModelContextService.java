package com.dereekb.gae.server.auth.security.model.context.service;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;

/**
 * Service used for building {@link LoginTokenModelContextSet} values from model
 * keys.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenModelContextService {

	/**
	 * Makes the context set.
	 * 
	 * @param request
	 *            {@link LoginTokenModelContextServiceRequest}. Never
	 *            {@code null}.
	 * @return {@link LoginTokenModelContextServiceResponse}. Never
	 *         {@code null}.
	 * @throws AtomicOperationException
	 *             thrown if the request is atomic and the request fails due to
	 *             atomic-related reasons.
	 */
	public LoginTokenModelContextServiceResponse makeContextSet(LoginTokenModelContextServiceRequest request)
	        throws AtomicOperationException;

	// Used for building contexts with a request, or something? Primarily need
	// to build/update a LoginToken with object credentials that lasts 5
	// minutes. The service would be used to load the model(s) and build
	// contexts and add them to the context set that is the result. Another
	// function then adds them to the login token and re-encodes it for 5
	// minutes. We need to be careful the re-encoding part cannot be used
	// maliciously. The token will live only as long as the input token, and up
	// to the max time (5 minutes).

	// Request is to get contexts for x models of type y (and can specify
	// multiple types and keys for each).

}
