package com.dereekb.gae.server.app.model.app.misc.owned;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * For types that are owned/associated with a {@link App}.
 *
 * @author dereekb
 *
 */
public interface AppOwnedModel
        extends UniqueModel {

	/**
	 * Gets the key for the associated {@link App}.
	 *
	 * @return {@link ModelKey}. {@code null} if no associated user.
	 */
	public ModelKey getAppOwnerKey();

}
