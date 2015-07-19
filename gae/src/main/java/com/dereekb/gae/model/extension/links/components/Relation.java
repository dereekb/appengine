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
	 * Returns the type specified in this {@link Relation}, if available.
	 *
	 * @return model type this {@link Relation} targets. Can be {@code null}.
	 */
	public String getRelationTargetType();

	/**
	 * {@link List} of {@link ModelKey} values in this {@link Relation}. This
	 * list cannot be empty.
	 *
	 * @return the collection of keys within this change. Never {@code null}.
	 */
	public List<ModelKey> getRelationKeys();

}
