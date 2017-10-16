package com.dereekb.gae.server.auth.security.model.query.task;

import com.dereekb.gae.server.auth.security.misc.task.AbstractSecurityTask;
import com.dereekb.gae.server.auth.security.model.query.MutableOwnedModelQuery;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Security task that restricts querying to administrator only.
 * <p>
 * Generally this shouldn't be relied on as much as proper security
 * configurations, just due to the overhead it adds vs those systems.
 * 
 * @author dereekb
 *
 */
public class SecurityOverrideAdminOnlyModelQueryTask
        implements Task<MutableOwnedModelQuery> {

	@Override
	public void doTask(MutableOwnedModelQuery input) throws FailedTaskException {
		LoginTokenAuthentication<LoginToken> authentication = AbstractSecurityTask.getAuthentication();
		LoginTokenUserDetails<LoginToken> details = authentication.getPrincipal();

		switch (details.getUserType()) {
			case ADMINISTRATOR:
				break;
			default:
				throw new FailedTaskException("Not allowed to query this type.");
		}
	}

	@Override
	public String toString() {
		return "SecurityOverrideOwnedModelQueryTask []";
	}

}
