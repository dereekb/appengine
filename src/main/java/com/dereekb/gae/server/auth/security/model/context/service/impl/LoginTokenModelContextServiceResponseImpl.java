package com.dereekb.gae.server.auth.security.model.context.service.impl;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceResponse;

/**
 * {@link LoginTokenModelContextServiceResponse} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextServiceResponseImpl
        implements LoginTokenModelContextServiceResponse {

	private LoginTokenModelContextSet contextSet;

	public LoginTokenModelContextServiceResponseImpl(LoginTokenModelContextSet contextSet) {
		this.setContextSet(contextSet);
	}

	// MARK: LoginTokenModelContextServiceResponse
	@Override
	public LoginTokenModelContextSet getContextSet() {
		return this.contextSet;
	}

	public void setContextSet(LoginTokenModelContextSet contextSet) {
		if (contextSet == null) {
			throw new IllegalArgumentException("contextSet cannot be null.");
		}

		this.contextSet = contextSet;
	}

	@Override
	public String toString() {
		return "LoginTokenModelContextServiceResponseImpl [contextSet=" + this.contextSet + "]";
	}

}
