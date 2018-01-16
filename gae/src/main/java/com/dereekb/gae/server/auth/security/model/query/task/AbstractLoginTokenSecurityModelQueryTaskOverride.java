package com.dereekb.gae.server.auth.security.model.query.task;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;

/**
 * Abstract {@link AbstractSecurityModelQueryTaskOverride}
 *
 * @author dereekb
 *
 * @param <Q>
 *            query type
 */
public abstract class AbstractLoginTokenSecurityModelQueryTaskOverride<Q> extends AbstractSecurityModelQueryTaskOverride<LoginTokenUserDetails<LoginToken>, Q> {

	@Override
	protected LoginTokenUserDetails<LoginToken> tryLoadSecurityDetails() throws NoSecurityContextException {
		return LoginSecurityContext.getPrincipal();
	}

}
