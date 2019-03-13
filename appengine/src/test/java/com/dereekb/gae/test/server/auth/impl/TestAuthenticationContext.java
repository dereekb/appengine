package com.dereekb.gae.test.server.auth.impl;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;

public interface TestAuthenticationContext {

	/**
	 * Resets/Initializes the system authentication context so that the
	 * {@link LoginSecurityContext} returns a context.
	 */
	public void resetContext();

}
