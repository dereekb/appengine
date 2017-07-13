package com.dereekb.gae.model.extension.links.components.model.change;

import java.util.Map;

import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Represents a change made in a {@link LinkModelSet}.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface LinkModelSetChange {

	/**
	 * Returns the {@link LinkModelSet} for this type.
	 *
	 * @return {@link LinkModelSet} containing these changes.
	 */
	public LinkModelSet getModelSet();

	/**
	 * @return {@link Map} of {@link LinkModelChange} for changes made to this
	 *         set.
	 */
	public Map<ModelKey, LinkModelChange> getModelChanges();

}
