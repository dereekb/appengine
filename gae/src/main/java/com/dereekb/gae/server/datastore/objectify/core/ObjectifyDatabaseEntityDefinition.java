package com.dereekb.gae.server.datastore.objectify.core;

import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * Represents an Objectify Definition for a single type. Used as part of the
 * {@link ObjectifyDatabase} initialization process.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface ObjectifyDatabaseEntityDefinition<T> {

	/**
	 * @return the system name of the entity.
	 */
	public String getEntityName();

	/**
	 * @return the {@link Class} of the entity.
	 */
	public Class<T> getEntityType();

	/**
	 *
	 * @return the type of key used.
	 */
	public ModelKeyType getEntityKeyType();

}
