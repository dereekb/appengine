package com.dereekb.gae.server.auth.security.model.query.task.impl;

import com.dereekb.gae.server.auth.security.model.query.task.AbstractLoginTokenSecurityModelQueryTaskOverride;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * Security task that extends/implements
 * {@link AbstractLoginTokenSecurityModelQueryTaskOverride} and does not effect
 * the input query at all.
 *
 * @author dereekb
 *
 * @param <Q>
 *            query type
 */
public class AllowAllSecurityModelQueryTask<Q> extends AbstractLoginTokenSecurityModelQueryTaskOverride<Q> {

	@Override
	protected void tryUpdateQueryForUser(Q input,
	                                     LoginTokenUserDetails<LoginToken> details)
	        throws InvalidAttributeException,
	            NoModelKeyException,
	            FailedTaskException {
		// Do nothing.
	}

	@Override
	public String toString() {
		return "AllowAllSecurityModelQueryTaskOverride[]";
	}

}
