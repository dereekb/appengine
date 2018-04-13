package com.dereekb.gae.server.auth.security.model.query.task.impl;

import java.util.List;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;

/**
 * {@link AbstractSecurityModelQueryTaskOverrideImpl} implementation for login token.
 *
 * @author dereekb
 *
 * @param <Q>
 *            query type
 */
public class LoginSecurityModelQueryTaskOverrideImpl<Q> extends AbstractSecurityModelQueryTaskOverrideImpl<LoginTokenUserDetails<LoginToken>, Q> {

	public LoginSecurityModelQueryTaskOverrideImpl(
	        AbstractSecurityModelQueryTaskOverrideDelegate<LoginTokenUserDetails<LoginToken>, Q> delegate) {
		super(delegate);
	}

	public LoginSecurityModelQueryTaskOverrideImpl(
	        List<AbstractSecurityModelQueryTaskOverrideDelegate<LoginTokenUserDetails<LoginToken>, Q>> delegates) {
		super(delegates);
	}

	// MARK: AbstractSecurityModelQueryTaskOverrideImpl
	@Override
	protected LoginTokenUserDetails<LoginToken> tryLoadSecurityDetails() throws NoSecurityContextException {
		return LoginSecurityContext.getPrincipal();
	}

	@Override
	public String toString() {
		return "LoginSecurityModelQueryTaskOverrideImpl []";
	}

}
