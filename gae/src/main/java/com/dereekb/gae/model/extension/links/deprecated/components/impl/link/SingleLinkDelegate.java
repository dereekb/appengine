package com.dereekb.gae.model.extension.links.components.impl.link;

import com.dereekb.gae.model.extension.links.deprecated.components.exception.RelationChangeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Delegate for {@link SingleLink}.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface SingleLinkDelegate {

	/**
	 * Returns the current {@link ModelKey} for the {@link Link}.
	 *
	 * @return The link's current {@link ModelKey}, or {@code null} if not set.
	 */
	public ModelKey getKey();

	/**
	 * Sets the new {@link ModelKey} for the {@link Link}.
	 *
	 * @param key
	 *            {@link ModelKey} to set, or {@code null} if clearing the
	 *            value.
	 * @throws RelationChangeException
	 *             thrown if the change is not allowed.
	 */
	public void setKey(ModelKey key) throws RelationChangeException;

}
