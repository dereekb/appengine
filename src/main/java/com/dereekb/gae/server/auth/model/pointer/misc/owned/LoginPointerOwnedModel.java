package com.dereekb.gae.server.auth.model.pointer.misc.owned;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * For types that are owned/associated with a {@link LoginPointer}.
 *
 * @author dereekb
 *
 */
public interface LoginPointerOwnedModel
        extends UniqueModel {

	/**
	 * Gets the key for the associated {@link LoginPointer}.
	 *
	 * @return {@link ModelKey}. {@code null} if no associated user.
	 */
	public ModelKey getLoginPointerOwnerKey();

}
