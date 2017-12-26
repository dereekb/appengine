package com.dereekb.gae.server.auth.model.login.misc.owned;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * For types that are owned/associated with a {@link Login}.
 *
 * @author dereekb
 *
 */
public interface LoginOwnedModel
        extends UniqueModel {

	/**
	 * Gets the key for the associated {@link Login}.
	 *
	 * @return {@link ModelKey}. {@code null} if no associated user.
	 */
	public ModelKey getLoginOwnerKey();

}
