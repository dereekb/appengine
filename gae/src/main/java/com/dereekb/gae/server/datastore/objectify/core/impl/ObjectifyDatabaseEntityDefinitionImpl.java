package com.dereekb.gae.server.datastore.objectify.core.impl;

import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityDefinition;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityKeyEnforcement;
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
	private ObjectifyDatabaseEntityKeyEnforcement keyEnforcement = ObjectifyDatabaseEntityKeyEnforcement.DEFAULT;

	public ObjectifyDatabaseEntityDefinitionImpl(String entityName, Class<?> entityType, ModelKeyType entityKeyType) {
		this(entityName, entityType, entityKeyType, null);
	}

	public ObjectifyDatabaseEntityDefinitionImpl(String entityName,
	        Class<?> entityType,
	        ModelKeyType entityKeyType,
	        ObjectifyQueryRequestLimitedBuilderInitializer queryInitializer) {
		this.setEntityName(entityName);
		this.setEntityType(entityType);
		this.setEntityKeyType(entityKeyType);
		this.setQueryInitializer(queryInitializer);
		this.validateName();
	}

	@Override
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		if (entityName == null) {
			throw new IllegalArgumentException("entityName cannot be null.");
		}

		this.entityName = entityName;
	}

	@Override
	public Class<?> getEntityType() {
		return this.entityType;
	}

	public void setEntityType(Class<?> entityType) {
		if (entityType == null) {
			throw new IllegalArgumentException("entityType cannot be null.");
		}

		this.entityType = entityType;
	}

	@Override
	public ModelKeyType getEntityKeyType() {
		return this.entityKeyType;
	}

	public void setEntityKeyType(ModelKeyType entityKeyType) {
		if (entityKeyType == null) {
			throw new IllegalArgumentException("entityKeyType cannot be null.");
		}

		this.entityKeyType = entityKeyType;
	}

	@Override
	public ObjectifyQueryRequestLimitedBuilderInitializer getQueryInitializer() {
		return this.queryInitializer;
	}

	public void setQueryInitializer(ObjectifyQueryRequestLimitedBuilderInitializer queryInitializer) {
		this.queryInitializer = queryInitializer;
	}

	protected void validateName() {
		if (this.entityType.getSimpleName().equalsIgnoreCase(this.entityName) == false) {
			throw new RuntimeException("Improperly configured ObjectifyDatabaseEntry. Has type '"
			        + this.entityType.getSimpleName() + "' but name '" + this.entityName + "'");
		}
	}

	@Override
	public ObjectifyDatabaseEntityKeyEnforcement getKeyEnforcement() {
		return this.keyEnforcement;
	}

	public void setKeyEnforcement(ObjectifyDatabaseEntityKeyEnforcement keyEnforcement) {
		if (keyEnforcement == null) {
			throw new IllegalArgumentException("keyEnforcement cannot be null.");
		}

		this.keyEnforcement = keyEnforcement;
	}

	@Override
	public String toString() {
		return "ObjectifyDatabaseEntityDefinitionImpl [entityName=" + this.entityName + ", entityType="
		        + this.entityType + ", entityKeyType=" + this.entityKeyType + "]";
	}

}
