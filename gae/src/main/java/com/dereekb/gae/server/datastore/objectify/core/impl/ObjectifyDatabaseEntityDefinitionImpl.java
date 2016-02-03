package com.dereekb.gae.server.datastore.objectify.core.impl;

import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityDefinition;

/**
 * Default {@link ObjectifyDatabaseEntityDefinition} implementation.
 *
 * @author dereekb
 *
 */
public class ObjectifyDatabaseEntityDefinitionImpl
        implements ObjectifyDatabaseEntityDefinition {

	private String entityName;
	private Class<?> entityType;
	private ModelKeyType entityKeyType;

	public ObjectifyDatabaseEntityDefinitionImpl(String entityName, Class<?> entityType, ModelKeyType entityKeyType) {
		this.setEntityName(entityName);
		this.setEntityType(entityType);
		this.setEntityKeyType(entityKeyType);
	}

	@Override
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Override
	public Class<?> getEntityType() {
		return this.entityType;
	}

	public void setEntityType(Class<?> entityType) {
		this.entityType = entityType;
	}

    @Override
	public ModelKeyType getEntityKeyType() {
		return this.entityKeyType;
	}

	public void setEntityKeyType(ModelKeyType entityKeyType) {
		this.entityKeyType = entityKeyType;
	}

	@Override
	public String toString() {
		return "ObjectifyDatabaseEntityDefinitionImpl [entityName=" + this.entityName + ", entityType="
		        + this.entityType + ", entityKeyType=" + this.entityKeyType + "]";
	}

}
