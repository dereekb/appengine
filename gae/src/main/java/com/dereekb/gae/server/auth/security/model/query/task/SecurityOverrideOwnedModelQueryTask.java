package com.dereekb.gae.server.auth.security.model.query.task;

import com.dereekb.gae.server.auth.security.model.query.MutableOwnedModelQuery;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * Security task that restricts a query to the user's ownerId depending on the
 * role the current security context has.
 *
 * Administrator roles are unaffected. Non-user roles are rejected.
 *
 * @author dereekb
 *
 * @deprecated Ownership roles are mostly deprecated in favor of model security
 *             roles.
 */
@Deprecated
public class SecurityOverrideOwnedModelQueryTask extends AbstractLoginTokenSecurityModelQueryTaskOverride<MutableOwnedModelQuery> {

	@Override
	protected void tryUpdateQueryForUser(MutableOwnedModelQuery input,
	                                     LoginTokenUserDetails<LoginToken> details)
	        throws InvalidAttributeException,
	            NoModelKeyException,
	            FailedTaskException {
		OwnershipRoles roles = details.getLoginToken().getOwnershipRoles();
		String ownerId = roles.getOwnerId();

		if (ownerId != null) {
			input.setEqualsOwnerId(ownerId);
		} else {
			throw new NoModelKeyException("Security has no ownership of this type.");
		}
	}

	@Override
	public String toString() {
		return "SecurityOverrideOwnedModelQueryTask []";
	}

}
