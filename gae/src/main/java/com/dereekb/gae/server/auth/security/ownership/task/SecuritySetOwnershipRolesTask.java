package com.dereekb.gae.server.auth.security.ownership.task;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.datastore.models.owner.MutableOwnedModel;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableTask} that sets all input {@link MutableOwnedModel} values to
 * match the owner returned by {@link LoginSecurityContext}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @deprecated The current context might not always be the user requesting it
 *             (I.E. an admin is performing the function on their behalf in an
 *             admin context.)
 */
@Deprecated
public class SecuritySetOwnershipRolesTask<T extends MutableOwnedModel>
        implements IterableTask<T> {

	// MARK: IterableTask
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		String ownerId = this.getSecurityOwnerId();

		if (ownerId != null) {
			SetOwnershipRolesTask<T> task = new SetOwnershipRolesTask<T>(ownerId);
			task.doTask(input);
		}
	}

	// MARK: Internal
	private String getSecurityOwnerId() {
		String ownerId = null;

		try {
			LoginTokenAuthentication<?> authentication = LoginSecurityContext.getAuthentication();
			LoginToken token = authentication.getCredentials().getLoginToken();
			OwnershipRoles ownershipRoles = token.getOwnershipRoles();
			ownerId = ownershipRoles.getOwnerId();
		} catch (NoSecurityContextException e) {

		}

		return ownerId;
	}

}
