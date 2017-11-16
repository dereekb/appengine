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
	 * Builds a context set for the request.
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

}
