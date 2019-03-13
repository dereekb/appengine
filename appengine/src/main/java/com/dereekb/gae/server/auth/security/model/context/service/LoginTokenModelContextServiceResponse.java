package com.dereekb.gae.server.auth.security.model.context.service;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;

/**
 * {@link LoginTokenModelContextService} response.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenModelContextServiceResponse {

	/**
	 * Returns the created {@link LoginTokenModelContextSet}.
	 * 
	 * @return {@link LoginTokenModelContextSet}. Never {@code null}.
	 */
	public LoginTokenModelContextSet getContextSet();

}
