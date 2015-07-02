package com.dereekb.gae.server.datastore.models.keys;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Checks whether or not the keys are equivalent between {@link UniqueModel}
 * models.
 *
 * @author dereekb
 *
 * @param <K>
 */
public interface KeyEquality {

	/**
	 * Returns true if the model's keys are the same.
	 *
	 * @param key
	 * @return
	 */
	public boolean keysEqual(UniqueModel model);

}
