package com.dereekb.gae.model.extension.links.components;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Represents a relation within a {@link Link}. It is used for making changes.
 *
 * @author dereekb
 *
 */
public interface Relation {

	/**
	 * Returns the type specified in the relation.
	 *
	 * @return model type this relation targets. Can be null.
	 */
	public String getRelationTargetType();

	/**
	 * @return the collection of keys within this change. Never null.
	 */
	public List<ModelKey> getRelationKeys();

}
