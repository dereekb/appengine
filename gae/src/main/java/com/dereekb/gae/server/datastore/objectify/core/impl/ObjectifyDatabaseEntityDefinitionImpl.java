package com.dereekb.gae.server.datastore.objectify.core.impl;

import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityDefinition;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;

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
	private ObjectifyQueryRequestLimitedBuilderInitializer queryInitializer;

	public ObjectifyDatabaseEntityDefinitionImpl(String entityName, Class<?> entityType, ModelKeyType entityKeyType) {
		this.setEntityName(entityName);
		this.setEntityType(entityType);
		this.setEntityKeyType(entityKeyType);
	}

	public ObjectifyDatabaseEntityDefinitionImpl(String entityName,
	        Class<?> entityType,
	        ModelKeyType entityKeyType,
	        ObjectifyQueryRequestLimitedBuilderInitializer queryInitializer) {
		this.entityName = entityName;
		this.entityType = entityType;
		this.entityKeyType = entityKeyType;
		this.queryInitializer = queryInitializer;
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
	public ObjectifyQueryRequestLimitedBuilderInitializer getQueryInitializer() {
		return this.queryInitializer;
	}

	public void setQueryInitializer(ObjectifyQueryRequestLimitedBuilderInitializer queryInitializer) {
		this.queryInitializer = queryInitializer;
	}

	@Override
	public String toString() {
		return "ObjectifyDatabaseEntityDefinitionImpl [entityName=" + this.entityName + ", entityType="
		        + this.entityType + ", entityKeyType=" + this.entityKeyType + "]";
	}

}
