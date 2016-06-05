package com.dereekb.gae.utilities.collections.parent.objectify;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;

/**
 * Object that contains a parent.
 *
 * @author dereekb
 *
 */
public interface ObjectifyChild<T extends ObjectifyModel<T>>
        extends ObjectifyModel<T> {

	/**
	 * Returns the parent object key.
	 *
	 * @return {@link Key} of parent, or {@code null} if no parent available.
	 */
	public Key<T> getParentKey();

}
