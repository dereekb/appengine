package com.dereekb.gae.server.auth.security.model.query.task;

import com.dereekb.gae.server.auth.security.misc.task.AbstractSecurityTask;
import com.dereekb.gae.server.auth.security.model.query.MutableOwnedModelQuery;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Security task that restricts a query to the user's ownerId depending on the
 * role the current security context has.
 * 
 * Administrator roles are unaffected. Non-user roles are rejected.
 * 
 * @author dereekb
 *
 */
public class SecurityOverrideOwnedModelQueryTask
        implements Task<MutableOwnedModelQuery> {

	@Override
	public void doTask(MutableOwnedModelQuery input) throws FailedTaskException {
		LoginTokenAuthentication authentication = AbstractSecurityTask.getAuthentication();
		LoginTokenUserDetails details = authentication.getPrincipal();

		switch (details.getUserType()) {
			case ADMINISTRATOR:
				// Do nothing to modify the input query.
				break;
			case USER:
				OwnershipRoles roles = details.getLoginToken().getOwnershipRoles();
				String ownerId = roles.getOwnerId();

				if (ownerId != null) {
					input.setEqualsOwnerId(ownerId);
					break;
				}

				// Fall through if there is no ownerId.
			default:
				throw new FailedTaskException("No owner is available for querying.");
		}
	}

	@Override
	public String toString() {
		return "SecurityOverrideOwnedModelQueryTask []";
	}

}
