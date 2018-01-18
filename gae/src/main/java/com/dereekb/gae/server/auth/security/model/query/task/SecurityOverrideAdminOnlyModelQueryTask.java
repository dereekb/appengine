package com.dereekb.gae.server.auth.security.model.query.task;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * Security task that restricts querying to administrator only.
 * <p>
 * Generally this shouldn't be relied on as much as proper security
 * configurations, just due to the overhead it adds versus those systems.
 * (I.E. secure routes only administrators can access.)
 *
 * @author dereekb
 *
 * @param <Q>
 *            query type
 */
public class SecurityOverrideAdminOnlyModelQueryTask<Q> extends AbstractLoginTokenSecurityModelQueryTaskOverride<Q> {

	@Override
	protected void tryUpdateQueryForUser(Q input,
	                                     LoginTokenUserDetails<LoginToken> details)
	        throws InvalidAttributeException,
	            NoModelKeyException,
	            FailedTaskException {
		this.throwInvalidAttributeForUnauthorizedUser();
	}

	@Override
	public String toString() {
		return "SecurityOverrideAdminOnlyModelQueryTask []";
	}

}
