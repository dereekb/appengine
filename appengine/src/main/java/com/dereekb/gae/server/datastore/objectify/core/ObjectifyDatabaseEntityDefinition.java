package com.dereekb.gae.server.datastore.objectify.core;

import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;

/**
 * Represents an Objectify Definition for a single type. Used as part of the
 * {@link ObjectifyDatabaseImpl} initialization process.
 *
 * @author dereekb
 */
public interface ObjectifyDatabaseEntityDefinition {

	/**
	 * @return {@link ObjectifyDatabaseEntityKeyEnforcement}. Never {@code null}.
	 */
	public ObjectifyDatabaseEntityKeyEnforcement getKeyEnforcement();

	/**
	 * @return the system name of the entity.
	 */
	public String getEntityName();

	/**
	 * @return the {@link Class} of the entity.
	 */
	public Class<?> getEntityType();

	/**
	 *
	 * @return the type of key used.
	 */
	public ModelKeyType getEntityKeyType();

	/**
	 * Returns an optional query initializer used for query generation.
	 *
	 * @return {@link ObjectifyQueryRequestLimitedBuilderInitializer} instance,
	 *         or {@code null}.
	 */
	public ObjectifyQueryRequestLimitedBuilderInitializer getQueryInitializer();

}
