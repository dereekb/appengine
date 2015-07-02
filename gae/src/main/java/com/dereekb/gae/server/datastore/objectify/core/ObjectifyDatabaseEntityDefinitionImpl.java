package com.dereekb.gae.server.datastore.objectify.core;

import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;

/**
 * Default {@link ObjectifyDatabaseEntityDefinition} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            Entity type
 */
public final class ObjectifyDatabaseEntityDefinitionImpl<T extends ObjectifyModel<T>>
        implements ObjectifyDatabaseEntityDefinition<T> {

	private final String name;
	private final Class<T> type;
	private final ModelKeyType keyType;

	public ObjectifyDatabaseEntityDefinitionImpl(String name, Class<T> type, ModelKeyType keyType) {
		this.name = name;
		this.type = type;
		this.keyType = keyType;
	}

	@Override
	public String getEntityName() {
		return this.name;
	}

	@Override
	public Class<T> getEntityType() {
		return this.type;
	}

	@Override
	public ModelKeyType getEntityKeyType() {
		return this.keyType;
	}

}
